package status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.api.Play;
import play.libs.Json;
import protocol.EtapaTransacao;
import protocol.TipoTransacao;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class Transacao {

    public static final String COLLECTION = "transacoes";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public EtapaTransacao etapaTransacao;

    public TipoTransacao tipoTransacao;

    public Long tempoMaximo;

    @MongoId
    @MongoObjectId
    public String transacaoId;

    public List<String> idControladores;

    public Long timestamp;

    public String payload;

    public Transacao() {
    }

    public Transacao(List<String> idControladores, String payload, TipoTransacao tipoTransacao) {
        this.etapaTransacao = EtapaTransacao.NEW;
        this.tipoTransacao = tipoTransacao;
        this.idControladores = idControladores;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public Transacao(String idControlador, String payload, TipoTransacao tipoTransacao) {
        this(Arrays.asList(idControlador), payload, tipoTransacao);
    }

    public static MongoCollection transacoes() {
        return jongo.getCollection(COLLECTION);
    }

    public static Transacao findByTransacaoId(String transacaoId) {
        return transacoes().findOne("{ transacaoId: # }", transacaoId).as(Transacao.class);
    }

    public static Transacao fromJson(JsonNode transacaoJson) {
        Transacao transacao = Json.fromJson(transacaoJson, Transacao.class);
        if (transacaoJson.has("controladores")) {
            transacao.idControladores = Json.fromJson(transacaoJson.get("controladores"), List.class);
        }
        if (transacaoJson.has("payload")) {
            transacao.setPayload(transacaoJson.get("payload").asText());
        }
        return transacao;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void create() {
        transacoes().insert(this);
    }

    public void update() {
        transacoes().save(this);
    }

    public void updateStatus(EtapaTransacao etapaTransacao) {
        this.etapaTransacao = etapaTransacao;
        this.timestamp = DateTime.now().getMillis();
    }

    @Override
    public String toString() {
        return "Transacao{" +
            ", etapaTransacao=" + etapaTransacao +
            ", tipoTransacao=" + tipoTransacao +
            ", tempoMaximo=" + tempoMaximo +
            ", transacaoId='" + transacaoId + '\'' +
            ", idControladores=" + idControladores +
            ", timestamp=" + timestamp +
            ", payload='" + payload + '\'' +
            '}';
    }

    public JsonNode toJson() {
        ObjectNode root = Json.newObject();
        root.put("transacaoId", transacaoId);
        root.put("etapaTransacao", etapaTransacao.toString());
        root.put("tipoTransacao", tipoTransacao.toString());
        root.put("timestamp", timestamp);

        ArrayNode controladores = root.putArray("controladores");
        idControladores.stream().forEach(id -> controladores.add(id));

        if (payload != null) {
            root.put("payload", payload.toString());
        }
        return root;
    }
}

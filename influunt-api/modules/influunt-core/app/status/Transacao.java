package status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jongo.MongoCollection;
import play.api.Play;
import play.libs.Json;
import protocol.EtapaTransacao;
import protocol.TipoTransacao;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.UUID;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class Transacao {

    public static final String COLLECTION = "transacoes";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public EtapaTransacao etapaTransacao;

    public TipoTransacao tipoTransacao;

    public String transacaoId;

    public String idControlador;

    public Long timestamp;

    public transient Object payload;

    public Transacao() {
    }

    public Transacao(String idControlador, Object payload, TipoTransacao tipoTransacao) {
        this.etapaTransacao = EtapaTransacao.NEW;
        this.tipoTransacao = tipoTransacao;
        this.transacaoId = UUID.randomUUID().toString();
        this.idControlador = idControlador;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public static MongoCollection transacoes() {
        return jongo.getCollection(COLLECTION);
    }

    public static Transacao findByTransacaoId(String transacaoId) {
        return transacoes().findOne("{ transacaoId: # }", transacaoId).as(Transacao.class);
    }

    public static Transacao fromJson(JsonNode transacaoJson) {
        Transacao transacao = Json.fromJson(transacaoJson, Transacao.class);
        if (transacaoJson.has("payload")) {
            transacao.setPayload(transacaoJson.get("payload").asText());
        }
        return transacao;
    }

    public void setPayload(Object payload) {
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
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transacao{" +
            "etapaTransacao=" + etapaTransacao +
            ", tipoTransacao='" + tipoTransacao + '\'' +
            ", transacaoId='" + transacaoId + '\'' +
            ", idControlador='" + idControlador + '\'' +
            '}';
    }

    public JsonNode toJson() {
        ObjectNode root = Json.newObject();
        root.put("transacaoId", transacaoId);
        root.put("etapaTransacao", etapaTransacao.toString());
        root.put("tipoTransacao", tipoTransacao.toString());
        root.put("idControlador", idControlador);
        root.put("timestamp", timestamp);
        if (payload != null) {
            root.put("payload", payload.toString());
        }
        return root;
    }
}

package status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.api.Play;
import play.libs.Json;
import protocol.TipoTransacao;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 12/8/16.
 */
public class PacoteTransacao {

    public static final String COLLECTION = "transacoes";
    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    @MongoId
    private String id;
    private StatusPacoteTransacao statusPacoteTransacao;
    private TipoTransacao tipoTransacao;
    private Long timestamp;
    private Long tempoMaximo;
    private List<Transacao> transacoes;

    public static MongoCollection transacoes() {
        return jongo.getCollection(COLLECTION);
    }

    public PacoteTransacao(){}

    public PacoteTransacao(TipoTransacao tipoTransacao, Long tempoMaximo) {
        this.timestamp = DateTime.now().getMillis();
        this.statusPacoteTransacao = StatusPacoteTransacao.NEW;
        this.tipoTransacao = tipoTransacao;
        this.tempoMaximo = tempoMaximo;
        this.id = UUID.randomUUID().toString();
    }

    public PacoteTransacao(TipoTransacao tipoTransacao, Long tempoMaximo, List<Transacao> transacoes) {
        this(tipoTransacao,tempoMaximo);
        this.transacoes = transacoes;
    }

    public PacoteTransacao(TipoTransacao tipoTransacao, Long tempoMaximo, Transacao transacao) {
        this(tipoTransacao,tempoMaximo);
        this.transacoes = Arrays.asList(transacao);
    }

    public static PacoteTransacao findByTransacaoId(String transacaoId) {
        return transacoes().findOne("{ transacaoId: # }", transacaoId).as(PacoteTransacao.class);
    }

    public static PacoteTransacao fromJson(JsonNode pacoteTransacaoJson) {
        return Json.fromJson(pacoteTransacaoJson, PacoteTransacao.class);
    }

    public JsonNode toJson() {
        ObjectNode root = Json.newObject();
        root.put("id", id);
        root.put("tempoMaximo", tempoMaximo);
        root.put("statusPacoteTransacao", statusPacoteTransacao.toString());
        root.put("tipoTransacao", tipoTransacao.toString());
        root.put("timestamp", timestamp);

        ArrayNode transacoesJson = root.putArray("transacoes");
        transacoes.stream().forEach(t -> transacoesJson.add(t.toJson()));

        return root;
    }


    public void create() {
        transacoes().insert(this);
    }

    public void update() {
        transacoes().save(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusPacoteTransacao getStatusPacoteTransacao() {
        return statusPacoteTransacao;
    }

    public void setStatusPacoteTransacao(StatusPacoteTransacao statusPacoteTransacao) {
        this.statusPacoteTransacao = statusPacoteTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTempoMaximo() {
        return tempoMaximo;
    }

    public void setTempoMaximo(Long tempoMaximo) {
        this.tempoMaximo = tempoMaximo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }


}

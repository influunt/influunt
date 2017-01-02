package status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.libs.Json;
import protocol.EtapaTransacao;
import protocol.TipoTransacao;

import java.util.UUID;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class Transacao {


    public EtapaTransacao etapaTransacao;

    public TipoTransacao tipoTransacao;

    public String transacaoId;

    public String idControlador;

    public Long timestamp;

    public String payload;

    public Transacao() {
    }

    public Transacao(String idControlador, String payload, TipoTransacao tipoTransacao) {
        this.transacaoId = UUID.randomUUID().toString();
        this.etapaTransacao = EtapaTransacao.NEW;
        this.tipoTransacao = tipoTransacao;
        this.idControlador = idControlador;
        this.payload = payload;
        this.timestamp = DateTime.now().getMillis();
    }

    public static Transacao fromJson(JsonNode transacaoJson) {
        return Json.fromJson(transacaoJson, Transacao.class);
    }

    public void updateStatus(EtapaTransacao etapaTransacao) {
        this.etapaTransacao = etapaTransacao;
        this.timestamp = DateTime.now().getMillis();
    }

    public JsonNode toJson() {
        ObjectNode root = Json.newObject();
        root.put("transacaoId", transacaoId);
        root.put("etapaTransacao", etapaTransacao.toString());
        root.put("tipoTransacao", tipoTransacao.toString());
        root.put("timestamp", timestamp);
        root.put("idControlador", idControlador);

        if (payload != null) {
            root.put("payload", payload);
        }
        return root;
    }

    @Override
    public String toString() {
        return "Transacao{" +
            "etapaTransacao=" + etapaTransacao +
            ", tipoTransacao=" + tipoTransacao +
            ", transacaoId='" + transacaoId + '\'' +
            ", idControlador='" + idControlador + '\'' +
            ", timestamp=" + timestamp +
            ", payload='" + payload + '\'' +
            '}';
    }

    public EtapaTransacao getEtapaTransacao() {
        return etapaTransacao;
    }

    public void setEtapaTransacao(EtapaTransacao etapaTransacao) {
        this.etapaTransacao = etapaTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public String getTransacaoId() {
        return transacaoId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


    public String getIdControlador() {
        return idControlador;
    }

    public void setIdControlador(String idControlador) {
        this.idControlador = idControlador;
    }
}

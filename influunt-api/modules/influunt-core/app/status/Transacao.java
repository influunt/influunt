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

import static status.PacoteTransacao.transacoes;

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

    public void setPayload(String payload) {
        this.payload = payload;
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
            root.put("payload", payload.toString());
        }
        return root;
    }
}

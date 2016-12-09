package status;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import protocol.EtapaTransacao;

public class TransactionPayload {

    @MongoId
    @MongoObjectId
    private String payloadId;

    private String controladorFisicoId;

    private String content;

    private EtapaTransacao etapaTransacao = EtapaTransacao.NEW;

    public TransactionPayload(String controladorFisicoId, String content) {
        this.controladorFisicoId = controladorFisicoId;
        this.content = content;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getControladorFisicoId() {
        return controladorFisicoId;
    }

    public void setControladorFisicoId(String controladorFisicoId) {
        this.controladorFisicoId = controladorFisicoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EtapaTransacao getEtapaTransacao() {
        return etapaTransacao;
    }

    public void setEtapaTransacao(EtapaTransacao etapaTransacao) {
        this.etapaTransacao = etapaTransacao;
    }
}

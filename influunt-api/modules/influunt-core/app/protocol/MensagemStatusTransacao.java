package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemStatusTransacao {

    public StatusTransacao status;

    private MensagemStatusTransacao(StatusTransacao status) {
        this.status = status;
    }

    public static Envelope getMensagem(Transacao transacao, StatusTransacao status) {
        String payload = Json.toJson(new MensagemStatusTransacao(status)).toString();
        Envelope envelope = new Envelope(TipoMensagem.STATUS_TRANSACAO, transacao.idControlador, DestinoApp.statusTransacao(transacao.transacaoId), QoS.AT_LEAST_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemLiberarImposicao {

    public int numeroAnel;

    public MensagemLiberarImposicao(int numeroAnel) {
        this.numeroAnel = numeroAnel;
    }

    public static Envelope getMensagem(String controladorId, int numeroAnel) {
        String payload = Json.toJson(new MensagemLiberarImposicao(numeroAnel)).toString();
        Envelope envelope = new Envelope(TipoMensagem.LIBERAR_IMPOSICAO, controladorId, null, QoS.AT_LEAST_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

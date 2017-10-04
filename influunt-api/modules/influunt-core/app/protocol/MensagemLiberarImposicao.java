package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

import java.util.List;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemLiberarImposicao {

    public List<Integer> numerosAneis;

    public MensagemLiberarImposicao(List<Integer> numerosAneis) {
        this.numerosAneis = numerosAneis;
    }

    public static Envelope getMensagem(String controladorId, List<Integer> numerosAneis) {
        String payload = Json.toJson(new MensagemLiberarImposicao(numerosAneis)).toString();
        Envelope envelope = new Envelope(TipoMensagem.LIBERAR_IMPOSICAO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

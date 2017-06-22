package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

import java.util.List;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemImposicaoModoOperacao {

    public String modoOperacao;

    public List<Integer> numerosAneis;

    public Long horarioEntrada;

    public int duracao;

    public MensagemImposicaoModoOperacao(String modoOperacao, List<Integer> numerosAneis, Long horarioEntrada, int duracao) {
        this.modoOperacao = modoOperacao;
        this.numerosAneis = numerosAneis;
        this.horarioEntrada = horarioEntrada;
        this.duracao = duracao;
    }

    public static Envelope getMensagem(String controladorId, String modoOperacao, List<Integer> numerosAneis, Long horarioEntrada, int duracao) {
        String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao, numerosAneis, horarioEntrada, duracao)).toString();
        Envelope envelope = new Envelope(TipoMensagem.IMPOSICAO_DE_MODO_OPERACAO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }
}

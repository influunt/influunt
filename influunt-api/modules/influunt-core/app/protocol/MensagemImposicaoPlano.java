package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

import java.util.List;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemImposicaoPlano {

    public int posicaoPlano;

    public List<Integer> numerosAneis;

    public Long horarioEntrada;

    public int duracao;

    public MensagemImposicaoPlano(int posicaoPlano, List<Integer> numerosAneis, Long horarioEntrada, int duracao) {
        this.posicaoPlano = posicaoPlano;
        this.numerosAneis = numerosAneis;
        this.duracao = duracao;
        this.horarioEntrada = horarioEntrada;
    }

    public static Envelope getMensagem(String controladorId, int posicaoPlano, List<Integer> numerosAneis, Long horarioEntrada, int duracao) {
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numerosAneis, horarioEntrada, duracao)).toString();
        Envelope envelope = new Envelope(TipoMensagem.IMPOSICAO_DE_PLANO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

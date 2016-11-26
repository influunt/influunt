package protocol;

import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemImposicaoPlano {

    public int posicaoPlano;
    public int numeroAnel;
    public Long horarioEntrada;
    public int duracao;

    public MensagemImposicaoPlano(int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        this.posicaoPlano = posicaoPlano;
        this.numeroAnel = numeroAnel;
        this.duracao = duracao;
        this.horarioEntrada = horarioEntrada;
    }

    public static Envelope getMensagem(String controladorId, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, horarioEntrada, duracao)).toString();
        Envelope envelope = new Envelope(TipoMensagem.IMPOSICAO_DE_PLANO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

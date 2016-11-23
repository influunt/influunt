package protocol;

import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemImposicaoPlano {

    public int posicaoPlano;
    public int numeroAnel;
    public int duracao;

    public MensagemImposicaoPlano(int posicaoPlano, int numeroAnel, int duracao) {
        this.posicaoPlano = posicaoPlano;
        this.numeroAnel = numeroAnel;
        this.duracao = duracao;
    }

    public static Envelope getMensagem(String controladorId, int posicaoPlano, int numeroAnel, int duracao) {
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, duracao)).toString();
        Envelope envelope = new Envelope(TipoMensagem.IMPOSICAO_DE_PLANO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }

}

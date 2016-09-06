package os72c.client.procolos;

import os72c.client.models.Interrupcao;

import java.util.Set;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class MensagemInterrupcao extends Mensagem {
    public final Set<Interrupcao> interrupcoes;

    public MensagemInterrupcao(Set<Interrupcao> interrupcoes){
        this.interrupcoes = interrupcoes;
    }

    @Override
    public String toString() {
        return "MensagemInterrupcao{" +
                "interrupcoes=" + interrupcoes +
                '}';
    }
}

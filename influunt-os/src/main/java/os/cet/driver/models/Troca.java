package os.cet.driver.models;

import akka.actor.Cancellable;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class Troca {

    private Long duracao;
    private Cancellable cancellable;

    private EstadoGrupo[] grupos;

    public Troca(long duracao, EstadoGrupo... grupos){
        this.grupos = grupos;
        this.duracao = duracao;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public EstadoGrupo[] getGrupos() {
        return grupos;
    }

    public void setGrupos(EstadoGrupo[] grupos) {
        this.grupos = grupos;
    }

    public Cancellable getCancellable() {
        return cancellable;
    }

    public void setCancellable(Cancellable cancellable) {
        this.cancellable = cancellable;
    }
}

package engine;

import models.Estagio;
import models.EstagioPlano;

public class IntervaloEstagio {

    private long duracao;

    private boolean entreverde = false;

    private EstagioPlano estagioPlano;

    private EstagioPlano estagioPlanoAnterior;

    public IntervaloEstagio(long duracao, boolean entreverde, EstagioPlano estagioPlano, EstagioPlano estagioPlanoAnterior) {
        this.duracao = duracao;
        this.entreverde = entreverde;
        this.estagioPlano = estagioPlano;
        this.estagioPlanoAnterior = estagioPlanoAnterior;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }

    public boolean isEntreverde() {
        return entreverde;
    }

    public void setEntreverde(boolean entreverde) {
        this.entreverde = entreverde;
    }

    public EstagioPlano getEstagioPlano() {
        return estagioPlano;
    }

    public void setEstagioPlano(EstagioPlano estagioPlano) {
        this.estagioPlano = estagioPlano;
    }

    public Estagio getEstagio() {
        return this.estagioPlano.getEstagio();
    }

    public EstagioPlano getEstagioPlanoAnterior() {
        return estagioPlanoAnterior;
    }


    @Override
    public String toString() {
        return "IntervaloEstagio{" +
                "duracao=" + duracao +
                ", entreverde=" + entreverde +
                ", estagioPlano=" + estagioPlano +
                '}';
    }

}

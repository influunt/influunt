package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

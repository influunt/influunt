package engine;

import models.Estagio;
import models.EstagioPlano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntervaloEstagio {

    private long duracao;

    private boolean entreverde = false;

    private EstagioPlano estagioPlano;

    private EstagioPlano estagioPlanoAnterior;

    private HashMap<Long, List<EventoMotor>> eventos = new HashMap<>();

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

    public EstagioPlano getEstagioPlano() {
        return estagioPlano;
    }

    public void setEstagioPlano(EstagioPlano estagioPlano) {
        this.estagioPlano = estagioPlano;
    }

    public Estagio getEstagio() {
        return this.estagioPlano.getEstagio();
    }

    public void setEstagioPlanoAnterior(EstagioPlano estagioPlanoAnterior) {
        this.estagioPlanoAnterior = estagioPlanoAnterior;
    }

    public EstagioPlano getEstagioPlanoAnterior() {
        return estagioPlanoAnterior;
    }

    public HashMap<Long, List<EventoMotor>> getEventos() {
        return eventos;
    }

    public void setEventos(HashMap<Long, List<EventoMotor>> eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return "IntervaloEstagio{" +
                "duracao=" + duracao +
                ", entreverde=" + entreverde +
                ", estagioPlano=" + estagioPlano +
                '}';
    }

    public void addEvento(long contadorIntervalo, EventoMotor eventoMotor) {
        if(!this.eventos.containsKey(contadorIntervalo)){
            this.eventos.put(contadorIntervalo,new ArrayList<>());
        }
        this.eventos.get(contadorIntervalo).add(eventoMotor);
    }

}

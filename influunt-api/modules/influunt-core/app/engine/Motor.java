package engine;

import models.Controlador;
import models.Evento;
import models.Plano;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sun.tools.doclint.Entity.ge;


/**
 * Created by rodrigosol on 9/26/16.
 */
public class Motor implements  EventoCallback, GerenciadorDeEstagiosCallback {

    private final DateTime inicioControlador;

    private final Controlador controlador;

    private final MotorCallback callback;

    private DateTime instante;

    private GerenciadorDeEventos gerenciadorDeEventos;

    private List<GerenciadorDeEstagios> estagios = new ArrayList<>();

    private Evento eventoAtual;

    private Map<DateTime,List<Pair<DateTime,Pair<GerenciadorDeEstagios,Evento>>>> agendamentos = new HashMap<>() ;


    public Motor(Controlador controlador, DateTime inicioControlador, DateTime inicioExecucao, MotorCallback callback) {
        this.callback = callback;
        this.controlador = controlador;
        this.inicioControlador = inicioControlador;
        gerenciadorDeEventos = new GerenciadorDeEventos();
        gerenciadorDeEventos.addEventos(controlador.getTabelaHoraria().getEventos());
        this.instante = inicioExecucao;
    }


    public void tick() {
        Evento evento = gerenciadorDeEventos.eventoAtual(instante);
        boolean iniciarGrupos = false;
        if (eventoAtual == null || !evento.equals(eventoAtual)) {
            callback.onTrocaDePlano(instante,eventoAtual,evento);
            if(eventoAtual == null){
                iniciarGrupos = true;
            }else{
                estagios.stream().forEach(gerenciadorDeEstagios -> {

                    DateTime momentoTroca = instante.plus(gerenciadorDeEstagios.proximaJanelaDeTroca());

                    if(!agendamentos.containsKey(momentoTroca)){
                        agendamentos.put(momentoTroca, new ArrayList<>());
                    }

                    final Pair<GerenciadorDeEstagios, Evento> agendamento =
                            new Pair<GerenciadorDeEstagios, Evento>(gerenciadorDeEstagios, evento);


                    agendamentos.get(momentoTroca).add(new Pair<DateTime, Pair<GerenciadorDeEstagios, Evento>>(instante,agendamento));
                });
            }
            eventoAtual = evento;
        }

        if(agendamentos.get(instante) != null){
            agendamentos.get(instante).stream().forEach(ge -> {
                ge.getSecond().getFirst().trocarPlano(getPlanos(ge.getSecond().getSecond()).get(ge.getSecond().getFirst().getAnel() - 1));
                callback.onTrocaDePlanoEfetiva(instante,ge.getFirst(),ge.getSecond().getFirst().getAnel(),ge.getSecond().getSecond());
            });
        }

        if (iniciarGrupos) {
            List<Plano> planos = getPlanos(eventoAtual);
            for (int i = 1; i <= planos.size(); i++) {
                estagios.add(new GerenciadorDeEstagios(i, inicioControlador, instante, planos.get(i - 1), this));
            }
        }

        estagios.forEach(e -> e.tick());
        instante = instante.plus(100);
    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioChange(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioEnds(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {
        callback.onCicloEnds(anel, numeroCiclos);
    }

    private List<Plano> getPlanos(Evento evento) {
        return controlador.getAneis().stream().sorted((a1, a2) -> a1.getPosicao().compareTo(a2.getPosicao()))
                .flatMap(anel -> anel.getPlanos().stream())
                .filter(plano -> plano.getPosicao() == evento.getPosicaoPlano())
                .collect(Collectors.toList());
    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
        if(eventoMotor.getTipoEvento().equals(TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR) ||
           eventoMotor.getTipoEvento().equals(TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE)){
            Integer anel = (Integer) eventoMotor.getParams()[1];
            estagios.get(anel - 1).onEvento(eventoMotor);
        }

    }
}

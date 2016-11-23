package engine;

import models.Anel;
import models.Controlador;
import models.Evento;
import models.Plano;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/26/16.
 */
public class Motor implements EventoCallback, GerenciadorDeEstagiosCallback {

    private final DateTime inicioControlador;

    private final Controlador controlador;

    private final MotorCallback callback;

    private final MonitorDeFalhas monitor;

    private DateTime instante;

    private GerenciadorDeTabelaHoraria gerenciadorDeTabelaHoraria;

    private List<GerenciadorDeEstagios> estagios = new ArrayList<>();

    private Evento eventoAtual;

    private int step = 0;

    public Motor(Controlador controlador, DateTime inicioControlador, DateTime inicioExecucao, MotorCallback callback) {

        this.callback = callback;
        this.controlador = controlador;
        this.inicioControlador = inicioControlador;
        this.gerenciadorDeTabelaHoraria = new GerenciadorDeTabelaHoraria();
        this.gerenciadorDeTabelaHoraria.addEventos(controlador.getTabelaHoraria().getEventos());
        this.instante = inicioExecucao;

        this.monitor = new MonitorDeFalhas(this, controlador.getAneis().stream().map(Anel::getDetectores)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()));


        this.controlador.getAneis().stream().forEach(anel ->
            anel.getGruposSemaforicos().forEach(grupoSemaforico ->
                grupoSemaforico.getVerdesConflitantes().forEach(verdesConflitantes ->
                    monitor.addVerdesConflitantes(verdesConflitantes.getOrigem().getPosicao(),
                        verdesConflitantes.getDestino().getPosicao())

                )
            )
        );
    }


    public void tick() throws Exception {
        Evento evento = gerenciadorDeTabelaHoraria.eventoAtual(instante);
        boolean iniciarGrupos = false;
        if (eventoAtual == null || !evento.equals(eventoAtual)) {
            callback.onTrocaDePlano(instante, eventoAtual, evento, getPlanos(evento).stream().map(p -> p.getModoOperacao().toString()).collect(Collectors.toList()));
            if (eventoAtual == null) {
                iniciarGrupos = true;
            } else {
                estagios.stream().forEach(gerenciadorDeEstagios -> {
                    gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(evento, getPlanos(evento).get(gerenciadorDeEstagios.getAnel() - 1), instante));
                });
            }
            eventoAtual = evento;
        }

        List<Plano> planos = getPlanos(eventoAtual);

        if (iniciarGrupos) {
            estagios = new ArrayList<>();
            for (int i = 1; i <= planos.size(); i++) {
                estagios.add(new GerenciadorDeEstagios(i, inicioControlador, instante, planos.get(i - 1), this, this));
            }
        }

        monitor.tick(instante, planos);
        estagios.forEach(e -> e.tick());
        instante = instante.plus(100);
        step++;
        monitor.endTick();
    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioChange(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
        monitor.onEstagioChange(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioEnds(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {
        callback.onCicloEnds(anel, numeroCiclos);
        monitor.onClicloEnds(anel);
    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        callback.onTrocaDePlanoEfetiva(agendamentoTrocaPlano);
    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
        monitor.handle(eventoMotor);
    }

    private List<Plano> getPlanos(Evento evento) {
        return controlador.getAneis().stream().sorted((a1, a2) -> a1.getPosicao().compareTo(a2.getPosicao()))
            .flatMap(anel -> anel.getPlanos().stream())
            .filter(plano -> plano.getPosicao().equals(evento.getPosicaoPlano()))
            .collect(Collectors.toList());
    }

    public List<GerenciadorDeEstagios> getEstagios() {
        return estagios;
    }

    public void setEstagios(List<GerenciadorDeEstagios> estagios) {
        this.estagios = estagios;
    }

    public Plano getPlanoAtual(Integer anel) {
        return getPlanos(eventoAtual).get(anel - 1);
    }

    public MonitorDeFalhas getMonitor() {
        return monitor;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public Evento getEventoAtual() {
        return eventoAtual;
    }

    public void onFalha(EventoMotor eventoMotor) {
        callback.onFalha(instante, eventoMotor);
    }

    public void onRemocaoFalha(EventoMotor eventoMotor) {
        callback.onRemocaoFalha(instante, eventoMotor);
    }

    public void onAlarme(EventoMotor eventoMotor) {
        callback.onAlarme(instante, eventoMotor);
    }
}

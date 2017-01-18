package engine;

import engine.eventos.GerenciadorDeEventos;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import models.Anel;
import models.Controlador;
import models.Evento;
import models.Plano;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/26/16.
 */
public class Motor implements EventoCallback, GerenciadorDeEstagiosCallback {


    private final MotorCallback callback;

    private final MonitorDeFalhas monitor;

    private Controlador controlador;

    private DateTime instante;

    private GerenciadorDeTabelaHoraria gerenciadorDeTabelaHoraria;

    private List<GerenciadorDeEstagios> estagios = new ArrayList<>();

    private Evento eventoAtual;

    private boolean entrarEmModoManualAbrupt = false;

    private boolean emModoManual = false;

    private boolean informadoBloqueado = false;

    private boolean informadoLiberado = false;

    private Controlador controladorTemporario = null;

    private Map<Integer, Boolean> aneisProntosParaManual = new HashMap<>();

    private Map<Integer, Boolean> aneisProntosParaTrocaEstagioManual = new HashMap<>();

    public DateTime getInstante() {
        return instante;
    }

    public Motor(Controlador controlador, DateTime inicioControlador, MotorCallback callback) {

        this.callback = callback;
        this.controlador = controlador;
        this.gerenciadorDeTabelaHoraria = new GerenciadorDeTabelaHoraria();
        this.gerenciadorDeTabelaHoraria.addEventos(controlador.getTabelaHoraria().getEventos());

        aneisProntosParaManual.clear();
        controlador.getAneisAtivos().stream().filter(Anel::isAceitaModoManual)
            .forEach(anel -> {
                aneisProntosParaManual.put(anel.getPosicao(), false);
                aneisProntosParaTrocaEstagioManual.put(anel.getPosicao(), false);
            });

        if (inicioControlador.getMillisOfSecond() > 0) {
            int diff = 1000 - inicioControlador.getMillisOfSecond();

            this.instante = inicioControlador.plus(diff);
            try {
                Thread.sleep(diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            this.instante = inicioControlador;
        }


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

    public void setControladorTemporario(Controlador controlador) {
        controladorTemporario = controlador;
    }

    public void onMudancaTabelaHoraria() {
        InfluuntLogger.log(NivelLog.NORMAL, TipoLog.EXECUCAO, "Mudança de tabela horária");
        alteraControlador();
    }

    public void tick() {
        Evento evento = gerenciadorDeTabelaHoraria.eventoAtual(instante);
        boolean iniciarGrupos = false;
        if (eventoAtual == null || !evento.equals(eventoAtual)) {

            if (controladorTemporario != null) {
                alteraControlador();
                evento = gerenciadorDeTabelaHoraria.eventoAtual(instante);
            }

            callback.onTrocaDePlano(instante, eventoAtual, evento, getPlanos(evento).stream().map(p -> p.getModoOperacao().toString()).collect(Collectors.toList()));
            if (eventoAtual == null) {
                iniciarGrupos = true;
            } else {
                Evento finalEvento = evento;
                estagios.stream().forEach(gerenciadorDeEstagios -> {
                    gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(finalEvento, getPlanos(finalEvento).get(gerenciadorDeEstagios.getAnel() - 1), instante));
                });
            }
            eventoAtual = evento;
        }

        List<Plano> planos = getPlanos(eventoAtual);

        if (iniciarGrupos) {
            estagios = new ArrayList<>();
            for (int i = 1; i <= planos.size(); i++) {
                estagios.add(new GerenciadorDeEstagios(i, instante, planos.get(i - 1), this, this));
            }
        }

        monitor.tick(instante, planos);
        estagios.forEach(GerenciadorDeEstagios::tick);
        estagios.forEach(GerenciadorDeEstagios::tickTempo);
        estagios.forEach(GerenciadorDeEstagios::tickMonitoramentos);

        instante = instante.plus(100);
        monitor.endTick();
    }

    private void alteraControlador() {
        GerenciadorDeTabelaHoraria novoGerenciador = new GerenciadorDeTabelaHoraria();
        novoGerenciador.addEventos(controladorTemporario.getTabelaHoraria().getEventos());
        controlador = controladorTemporario;
        this.gerenciadorDeTabelaHoraria = novoGerenciador;
        controladorTemporario = null;
    }

    @Override
    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioChange(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
        monitor.onEstagioChange(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onEstagioModify(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioModify(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        callback.onEstagioEnds(anel, numeroCiclos, tempoDecorrido, timestamp, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, int numeroCiclos, Long tempoDecorrido) {
        callback.onCicloEnds(anel, numeroCiclos, tempoDecorrido);
        monitor.onClicloEnds(anel, numeroCiclos);
    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        if (!agendamentoTrocaPlano.isImpostoPorFalha() && !agendamentoTrocaPlano.isImposicaoPlano()) {
            Plano plano = agendamentoTrocaPlano.getPlano();
            InfluuntLogger.log(NivelLog.NORMAL, TipoLog.EXECUCAO,
                "Troca de plano por tabela horária. Plano " + plano.getPosicao() + " - " + plano.getModoOperacao());
        }
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
        InfluuntLogger.log(NivelLog.NORMAL, TipoLog.EXECUCAO, eventoMotor);
    }

    public MotorCallback getCallback() {
        return callback;
    }

    public void desativaModoManual(int anel, DateTime instante) {
        verificaAneisEmFalha(aneisProntosParaManual, false);
        if (!aneisProntosParaManual.values().contains(Boolean.FALSE)) {
            aneisProntosParaManual.put(anel, false);

            callback.modoManualDesativado(instante);
            emModoManual = false;
        } else {
            aneisProntosParaManual.put(anel, false);
        }

        entrarEmModoManualAbrupt = false;
    }

    public boolean ativaModoManual(int anel) {
        verificaAneisEmFalha(aneisProntosParaManual, true);

        entrarEmModoManualAbrupt = true;

        aneisProntosParaManual.put(anel, true);

        List<GerenciadorDeEstagios> aneisComManual = estagios.stream()
            .filter(gerenciador -> gerenciador.getPlano().getAnel().isAceitaModoManual() && gerenciador.getAnel() != anel)
            .collect(Collectors.toList());

        aneisComManual.stream()
            .forEach(gerenciador -> {
                if (!aneisProntosParaManual.get(gerenciador.getAnel())) {
                    GerenciadorDeEventos.entrarEmModoManual(gerenciador);
                }
            });

        if (aneisProntosParaManual.values().contains(Boolean.FALSE)) {
            emModoManual = false;
        } else {
            aneisComManual.stream()
                .forEach(gerenciador -> {
                    if (!gerenciador.isEmFalha()) {
                        gerenciador.executaAgendamentoTrocaDePlano();
                        gerenciador.verificaETrocaEstagio(gerenciador.getIntervalo());
                    }
                });
            callback.modoManualAtivo(instante);
            callback.trocaEstagioManualBloqueada(instante);
            informadoBloqueado = true;
            emModoManual = true;
        }

        return emModoManual;
    }

    private void verificaAneisEmFalha(Map<Integer, Boolean> lista, boolean value) {
        estagios.stream().filter(GerenciadorDeEstagios::isEmFalha).forEach(estagio -> {
            lista.put(estagio.getAnel(), value);
        });
    }

    public void bloqueaTrocaEstagioManual(int anel) {
        verificaAneisEmFalha(aneisProntosParaTrocaEstagioManual, false);
        aneisProntosParaTrocaEstagioManual.put(anel, false);

        if (aneisProntosParaTrocaEstagioManual.values().contains(Boolean.FALSE) && !informadoBloqueado) {
            callback.trocaEstagioManualBloqueada(instante);
            informadoBloqueado = true;
            informadoLiberado = false;
        }
    }

    public void liberaTrocaEstagioManual(int anel) {
        verificaAneisEmFalha(aneisProntosParaTrocaEstagioManual, true);
        aneisProntosParaTrocaEstagioManual.put(anel, true);

        if (aneisProntosParaTrocaEstagioManual.values().stream().allMatch(v -> v.equals(Boolean.TRUE)) && !informadoLiberado) {
            callback.trocaEstagioManualLiberada(instante);
            informadoBloqueado = false;
            informadoLiberado = true;
        }
    }

    public boolean podeTrocarEstagioManual() {
        return aneisProntosParaTrocaEstagioManual.values().stream().allMatch(v -> v.equals(Boolean.TRUE));
    }

    public boolean isEntrarEmModoManualAbrupt() {
        return entrarEmModoManualAbrupt;
    }

    public boolean isEmModoManual() {
        return emModoManual;
    }

    public void stop() {
        InfluuntLogger.log(NivelLog.NORMAL, TipoLog.FINALIZACAO, "Terminando a execução do motor");
    }
}

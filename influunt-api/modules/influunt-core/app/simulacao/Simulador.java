package simulacao;

import engine.EventoMotor;
import engine.Motor;
import engine.MotorCallback;
import models.*;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rodrigosol on 9/28/16.
 */
public class Simulador implements MotorCallback {
    private Controlador controlador;

    private DateTime dataInicioControlador;

    private DateTime inicio;

    private DateTime fim;

    private Map<DateTime, List<EventoMotor>> eventos = new HashMap<>();

    private LogSimulacao logSimulacao = new LogSimulacao();

    private Motor motor;

    private int tempoSimulacao = 0;

    public Simulador() {

    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setDataInicioControlador(DateTime dataInicioControlador) {
        this.dataInicioControlador = dataInicioControlador;
    }

    public void setInicio(DateTime inicio) {
        this.inicio = inicio;
    }

    public void setFim(DateTime fim) {
        this.fim = fim;
    }

    public void addEvento(EventoMotor eventoMotor) {
        if (!this.eventos.containsKey(eventoMotor.getTimestamp())) {
            this.eventos.put(eventoMotor.getTimestamp(), new ArrayList<>());
        }
        this.eventos.get(eventoMotor.getTimestamp()).add(eventoMotor);
    }

    public Detector getDetector(TipoDetector tipoDetector, int detector) {
        return controlador.getDetectores().stream()
                .filter(detector1 -> detector1.getTipo().equals(tipoDetector) && detector1.getPosicao().equals(detector))
                .findFirst().orElse(null);
    }

    public List<Plano> getPlano(int plano) {
        return controlador.getAneis().stream()
                .flatMap(anel -> anel.getPlanos().stream()).filter(plano1 -> plano1.getPosicao().equals(plano))
                .collect(Collectors.toList());
    }

    public void simular() {
        motor = new Motor(controlador, this);
        motor.start(inicio);
        while (inicio.getMillis() / 1000 < fim.getMillis() / 1000) {
            processaEventos(inicio);
            motor.tick(inicio);
            tempoSimulacao++;
            inicio = inicio.plusSeconds(1);
        }
        motor.stop(inicio);
    }

    private void processaEventos(DateTime inicio) {
        if(eventos.containsKey(inicio)) {
            eventos.get(inicio).stream().forEach(eventoMotor -> motor.onEvento(eventoMotor));
        }
    }

    @Override
    public void onStart(DateTime timestamp) {
        logSimulacao.log(new DefaultLog(timestamp, TipoEventoLog.INICIO_EXECUCAO));
    }

    @Override
    public void onStop(DateTime timestamp) {
        logSimulacao.log(new DefaultLog(timestamp, TipoEventoLog.INICIO_EXECUCAO));
    }

    @Override
    public void onChangeEvento(DateTime timestamp, models.Evento eventoAntigo, models.Evento eventoNovo) {
        //Todo - Refletir os planos por aneis
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY - EEE - HH:mm:ss");
        logSimulacao.log(new AlteracaoEventoLog(timestamp, eventoAntigo, eventoNovo,1));
    }

    @Override
    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {
        logSimulacao.log(new AlteracaoEstadoLog(timestamp, estadoAntigo, estadoNovo));
    }

    public int getTempoSimulacao() {
        return tempoSimulacao;
    }

    public EventoLog findInLog(TipoEventoLog tipoEventoLog, DateTime timestamp, int planoAnterior, int planoAtual, int anel) {
        return logSimulacao.filter(tipoEventoLog,timestamp,planoAnterior,planoAtual,anel);
    }

    public Stream<EventoLog> findInLog(TipoEventoLog alteracaoEstado, DateTime timestamp) {
        return logSimulacao.find(alteracaoEstado,timestamp);
    }

    public EventoLog findFirstInLog(TipoEventoLog alteracaoEstado, DateTime timestamp) {
        return findInLog(alteracaoEstado,timestamp).findFirst().orElse(null);
    }
}

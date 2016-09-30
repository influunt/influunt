package simulacao;

import engine.EventoMotor;
import engine.Motor;
import engine.MotorCallback;
import models.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        while (inicio.getMillis() / 1000 <= fim.getMillis() / 1000) {
            motor.tick(inicio);
            inicio = inicio.plusSeconds(1);
        }
        motor.stop(inicio);
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
        logSimulacao.log(new AlteracaoEventoLog(timestamp, eventoAntigo, eventoNovo));
    }

    @Override
    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {
        logSimulacao.log(new AlteracaoEstadoLog(timestamp, estadoAntigo, estadoNovo));
    }
}

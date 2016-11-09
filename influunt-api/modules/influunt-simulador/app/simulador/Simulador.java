package simulador;

import engine.EventoMotor;
import engine.Motor;
import engine.MotorCallback;
import models.Controlador;
import models.Detector;
import models.Plano;
import models.TipoDetector;
import models.simulador.parametros.ParametroSimulacao;
import models.simulador.parametros.ParametroSimulacaoDetector;
import models.simulador.parametros.ParametroSimulacaoManual;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/28/16.
 */
public abstract class Simulador implements MotorCallback {
    private final ParametroSimulacao parametros;

    protected DateTime dataInicioControlador;

    private Controlador controlador;

    private DateTime inicio;

    private DateTime fim;

    private Map<DateTime, List<EventoMotor>> eventos = new HashMap<>();



    private Motor motor;



    private DateTime ponteiro;

    private long tempoSimulacao = 0L;

    public Simulador(DateTime inicioSimulado, Controlador controlador, ParametroSimulacao parametros) {
        this.controlador = controlador;
        this.dataInicioControlador = inicioSimulado;
        this.parametros = parametros;
        setup(inicioSimulado, controlador, parametros);
    }

    private void setup(DateTime inicioSimulado, Controlador controlador, ParametroSimulacao parametros) {
        motor = new Motor(controlador, parametros.getInicioControlador(), inicioSimulado, this);
        this.parametros.getDetectores().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getImposicoes().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getFalhas().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getInsercaoDePlugDeControleManual().stream().forEach(param -> addEvento(param.toEvento()));
        this.ponteiro = parametros.getInicioSimulacao();
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setDataInicioControlador(DateTime dataInicioControlador) {
        this.dataInicioControlador = dataInicioControlador;
    }

    public void addEvento(EventoMotor eventoMotor) {
        if (!this.eventos.containsKey(eventoMotor.getTimestamp())) {
            this.eventos.put(eventoMotor.getTimestamp(), new ArrayList<>());
        }
        this.eventos.get(eventoMotor.getTimestamp()).add(eventoMotor);
    }

    public Detector getDetector(int anel, TipoDetector tipoDetector, int detector) {
        return controlador.getAneis().stream()
                .filter(a -> a.getPosicao().equals(anel)).findFirst()
                .get().getDetectores().stream().filter(detector1 -> detector1.getTipo().equals(tipoDetector) && detector1.getPosicao().equals(detector))
                .findFirst().orElse(null);
    }

    public List<Plano> getPlano(int plano) {
        return controlador.getAneis().stream()
                .flatMap(anel -> anel.getPlanos().stream()).filter(plano1 -> plano1.getPosicao().equals(plano))
                .collect(Collectors.toList());
    }

    public void simular(DateTime inicio, DateTime fim) {
        DateTime inicioSimulacao = inicio;
        while (inicioSimulacao.getMillis() / 100 < fim.getMillis() / 100) {
            processaEventos(inicioSimulacao);
            motor.tick();
            tempoSimulacao += 100;
            inicioSimulacao = inicioSimulacao.plus(100);
        }
    }

    private void processaEventos(DateTime inicio) {
        if (eventos.containsKey(inicio)) {
            eventos.get(inicio).stream().forEach(eventoMotor -> motor.onEvento(eventoMotor));
        }
    }

    public void avancar(long millis) {
        DateTime fim = ponteiro.plus(millis);
        simular(ponteiro, fim);
        ponteiro = fim;
    }


    public void detectorAcionador(int anel,TipoDetector tipoDetector,DateTime disparo,int detector) {
        ParametroSimulacaoDetector param = new ParametroSimulacaoDetector();
        param.setDetector(getDetector(anel,tipoDetector,detector));
        param.setDisparo(disparo);
        this.parametros.getDetectores().add(param);
        setup(dataInicioControlador, controlador, parametros);
    }

    public void alternarModoManual(DateTime disparo, boolean ativar) {
        ParametroSimulacaoManual param = new ParametroSimulacaoManual(disparo, ativar);
        this.parametros.getInsercaoDePlugDeControleManual().add(param);
        setup(dataInicioControlador, controlador, parametros);
    }
}

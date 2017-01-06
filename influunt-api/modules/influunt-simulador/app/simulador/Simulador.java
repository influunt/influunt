package simulador;

import com.fasterxml.jackson.databind.JsonNode;
import engine.EventoMotor;
import engine.Motor;
import engine.MotorCallback;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Cidade;
import models.Controlador;
import models.Plano;
import models.TipoDetector;
import models.simulador.parametros.ParametroSimulacao;
import models.simulador.parametros.ParametroSimulacaoDetector;
import models.simulador.parametros.ParametroSimulacaoManual;
import models.simulador.parametros.ParametroSimulacaoTrocaDeEstagioManual;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import play.Logger;
import utils.RangeUtils;

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

    private final JsonNode controladorOriginal;

    protected DateTime dataInicioControlador;

    private Map<DateTime, List<EventoMotor>> eventos = new HashMap<>();

    private Motor motor;

    private DateTime ponteiro;

    private long tempoSimulacao = 0L;

    public Simulador(Controlador controlador, ParametroSimulacao parametros) {
        this.dataInicioControlador = parametros.getInicioControlador();
        this.parametros = parametros;
        this.controladorOriginal = new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null));
        setup(parametros);
    }

    private void setup(ParametroSimulacao parametros) {
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorOriginal);
        if(motor != null){
            motor.stop();
        }
        motor = new Motor(controlador, parametros.getInicioControlador(), this);
        this.eventos.clear();
        this.parametros.getDetectores().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getImposicoes().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getImposicoesModos().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getLiberacoesImposicoes().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getFalhas().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getAlarmes().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getInsercaoDePlugDeControleManual().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getTrocasEstagioModoManual().stream().forEach(param -> addEvento(param.toEvento()));
        this.ponteiro = parametros.getInicioControlador();
    }


    public void addEvento(EventoMotor eventoMotor) {
        if (!this.eventos.containsKey(eventoMotor.getTimestamp())) {
            this.eventos.put(eventoMotor.getTimestamp(), new ArrayList<>());
        }
        this.eventos.get(eventoMotor.getTimestamp()).add(eventoMotor);
    }

    public void simular(DateTime fim) {
        DateTime inicioSimulacao = dataInicioControlador;
        setup(parametros);

        while (inicioSimulacao.getMillis() / 100 < fim.getMillis() / 100) {

            try {
                processaEventos(inicioSimulacao);
                motor.tick();
                tempoSimulacao += 100;
                inicioSimulacao = inicioSimulacao.plus(100);
            } catch (Exception e) {
                Logger.info("******************MORREU**********************");
                Logger.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void processaEventos(DateTime inicio) throws Exception {
        if (eventos.containsKey(inicio)) {
            eventos.get(inicio).stream().forEach(eventoMotor -> motor.onEvento(eventoMotor));
        }
    }


    public void detectorAcionador(int anel, TipoDetector tipoDetector, DateTime disparo, int detector) {
        ParametroSimulacaoDetector param = new ParametroSimulacaoDetector();
        param.setDetector(new Pair<Integer, TipoDetector>(detector, tipoDetector));
        param.setAnel(anel);
        param.setDisparo(disparo);

        this.parametros.removeEventos(disparo);

        this.parametros.getDetectores().add(param);
    }

    public void alternarModoManual(DateTime disparo, boolean ativar) {
        ParametroSimulacaoManual param = new ParametroSimulacaoManual(disparo, ativar);

        this.parametros.removeEventos(disparo);

        this.parametros.getInsercaoDePlugDeControleManual().add(param);
    }

    public void trocarEstagioModoManual(DateTime disparo) {
        ParametroSimulacaoTrocaDeEstagioManual param = new ParametroSimulacaoTrocaDeEstagioManual(disparo);

        this.parametros.removeEventos(disparo);

        this.parametros.getTrocasEstagioModoManual().add(param);
    }
}

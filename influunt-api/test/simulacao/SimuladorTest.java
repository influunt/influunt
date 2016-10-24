package simulacao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
import engine.IntervaloGrupoSemaforico;
import integracao.ControladorHelper;
import models.Controlador;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import models.simulador.parametros.ParametroSimulacaoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Test;
import simulador.Simulador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 10/17/16.
 */
public class SimuladorTest extends WithInfluuntApplicationNoAuthentication {
    private HashMap<Integer, List<Pair<DateTime, IntervaloGrupoSemaforico>>> estagios = new HashMap();

    @Test
    public void simualarTest() {
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        DateTime inicioSimulacao = new DateTime(2016, 9, 18, 0, 0, 0);
        ParametroSimulacao parametroSimulacao = new ParametroSimulacao();
        parametroSimulacao.setControlador(controlador);
        parametroSimulacao.setInicioControlador(inicioSimulacao);
        parametroSimulacao.setInicioSimulacao(inicioSimulacao);
        ArrayList<ParametroSimulacaoDetector> detectores = new ArrayList<ParametroSimulacaoDetector>();
        ParametroSimulacaoDetector detector = new ParametroSimulacaoDetector();
        detector.setDetector(controlador.getAneis().stream().filter(anel -> anel.getPosicao().equals(3)).findFirst().get().getDetectores().get(0));
        detector.setDisparo(new DateTime(2016, 9, 18, 0, 1, 0));
        detectores.add(detector);
        parametroSimulacao.setDetectores(detectores);


        Simulador simulador = new Simulador(inicioSimulacao, controlador, parametroSimulacao) {
            @Override
            public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual) {

            }

            @Override
            public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

            }

            @Override
            public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
                if (!estagios.containsKey(anel)) {
                    estagios.put(anel, new ArrayList<>());
                }
                estagios.get(anel).add(new Pair<DateTime, IntervaloGrupoSemaforico>(timestamp, intervalos));

            }

            @Override
            public void onCicloEnds(int anel, Long numeroCiclos) {

            }

            @Override
            public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

            }
        };

        simulador.simular(inicioSimulacao, inicioSimulacao.plusSeconds(300));

        System.out.println(getJson(parametroSimulacao));

    }

    public String getJson(ParametroSimulacao params) {

        StringBuffer sb = new StringBuffer("\"estagios\":{");
        String sbAnel = estagios.keySet().stream().map(key -> {

            String buffer = estagios.get(key).stream().map(e -> {
                System.out.println(e.getFirst().minus(params.getInicioSimulacao().getMillis()).getMillis());
                return e.getSecond().toJson(e.getFirst().minus(params.getInicioSimulacao().getMillis()));
            }).collect(Collectors.joining(",")) + "]";

            return "\"" + key.toString() + "\":[" + buffer;
        }).collect(Collectors.joining(","));

        return "{\"aneis\":{" + sbAnel.toString() + "}}";
    }


}
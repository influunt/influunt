package engine;

import com.google.common.collect.RangeMap;
import models.EstadoGrupoSemaforico;
import models.Plano;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 11/9/16.
 */
public class MonitorDeFalhas {


    private final MotorEventoHandler motorEventoHandler;

    private final Pattern sequenciaDeCores = Pattern.compile("(^(0)+$)|(^(3)+$)|(^(1)+$)|(^(5)+$)|(^(3)+(5)+$)|(^(3)+(0)+$)|(^(3)+(1)+$)|(^(5)+(3)+$)|(^(0)+(3)+$)|(^(0)+(5)+(3)+$)|(^((5)+(3)+|(0)+(5)+(3)+)(1)+$)|((((2)+|(1)+(2)+)(3)+|((2)+|(1)+(2)+)(6)+(3)+)($|(5)+|(0)+))|((((4)+|(1)+(4)+)(3)+|((4)+|(1)+(4)+)(6)+(3)+)($|(5)+|(0)+))");

    private long ticks = 0L;

    private Map<Integer, TreeSet<Integer>> conflitos = new HashMap<>();

    private Map<Integer, Integer> cicloDoVerdeConflitante = new HashMap<>();

    private Map<Integer, Long> retiraVerdeConflitantes = new HashMap<>();

    public MonitorDeFalhas(MotorEventoHandler motorEventoHandler) {
        this.motorEventoHandler = motorEventoHandler;
    }

    public void tick(DateTime timestamp, List<Plano> planos) {
        ticks += 100L;
        for (Map.Entry<Integer, Long> entry : retiraVerdeConflitantes.entrySet()) {
            if (entry.getValue().equals(ticks)) {
                motorEventoHandler.handle(new EventoMotor(timestamp, TipoEvento.FALHA_VERDES_CONFLITANTES_REMOCAO, entry.getKey(), planos.get(entry.getKey() - 1)));
                retiraVerdeConflitantes.remove(entry.getKey());
            }
        }
    }

    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!sequenciaCoresValida(intervalos)) {
            motorEventoHandler.handle(new EventoMotor(timestamp, TipoEvento.FALHA_SEQUENCIA_DE_CORES, anel));
        }

        if (verdesConflitantes(intervalos)) {
            if (!cicloDoVerdeConflitante.containsKey(anel)) {
                cicloDoVerdeConflitante.put(anel, 3);
                retiraVerdeConflitantes.put(anel, ticks + 10000L);
            }
            motorEventoHandler.handle(new EventoMotor(timestamp, TipoEvento.FALHA_VERDES_CONFLITANTES, anel));
        }
    }

    private boolean verdesConflitantes(IntervaloGrupoSemaforico intervalos) {
        return intervalos.getEstados().entrySet().stream().map(integerRangeMapEntry -> {
            if (conflitos.containsKey(integerRangeMapEntry.getKey())) {
                return conflita(integerRangeMapEntry.getValue(), conflitos.get(integerRangeMapEntry.getKey())
                    .stream().map(integer -> intervalos.getEstados()
                        .get(integer)).collect(Collectors.toList()));
            } else {
                return false;
            }
        }).anyMatch(Boolean::booleanValue);
    }


    private boolean conflita(RangeMap<Long, EstadoGrupoSemaforico> origem, List<RangeMap<Long, EstadoGrupoSemaforico>> outros) {

        return origem.asDescendingMapOfRanges().entrySet().stream().filter(rangeEstadoGrupoSemaforicoEntry ->
            rangeEstadoGrupoSemaforicoEntry.getValue().equals(EstadoGrupoSemaforico.VERDE)
        ).map(rangeEstadoGrupoSemaforicoEntry -> {
            for (long i = rangeEstadoGrupoSemaforicoEntry.getKey().lowerEndpoint(); i < rangeEstadoGrupoSemaforicoEntry.getKey().upperEndpoint(); i += 100) {
                long finalI = i;
                if (outros.stream().anyMatch(outro -> !outro.get(finalI).equals(EstadoGrupoSemaforico.VERMELHO))) {
                    return true;
                }
            }
            return false;
        }).anyMatch(Boolean::booleanValue);
    }

    private boolean sequenciaCoresValida(IntervaloGrupoSemaforico intervalos) {
        return intervalos.getEstados().entrySet().stream().map(entry -> {
            return entry.getValue().asMapOfRanges().values()
                .stream()
                .map(estado -> String.valueOf(estado.ordinal()))
                .collect(Collectors.joining());
        }).allMatch(s -> {
            if (!sequenciaDeCores.matcher(s).matches()) {
                System.out.println(s);
            }
            return sequenciaDeCores.matcher(s).matches();
        });

    }


    public void addVerdesConflitantes(Integer a, Integer b) {
        if (!conflitos.containsKey(a)) {
            conflitos.put(a, new TreeSet<>());
        }
        conflitos.get(a).add(b);
    }

    public void onClicloEnds(int anel) {
        if (cicloDoVerdeConflitante.containsKey(anel)) {
            Integer ciclo = cicloDoVerdeConflitante.get(anel);
            if (--ciclo > 0) {
                cicloDoVerdeConflitante.put(anel, ciclo);
            } else {
                cicloDoVerdeConflitante.remove(anel);
            }

        }
    }
}

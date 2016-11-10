package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.EstadoGrupoSemaforico;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 11/9/16.
 */
public class MonitorDeFalhas {


    private final MotorEventoHandler motorEventoHandler;

    //TODO: Avaliar se necessário separar por tipo de grupo semaforico
    private final Pattern sequenciaDeCores = Pattern.compile("(^(0)+$)|(^(3)+$)|(^(1)+$)|(^(5)+$)|(^(3)+(5)+$)|(^(3)+(0)+$)|(^(3)+(1)+$)|(^(5)+(3)+$)|(^(0)+(3)+$)|(^(0)+(5)+(3)+$)|(^((5)+(3)+|(0)+(5)+(3)+)(1)+$)|((((2)+|(1)+(2)+)(3)+|((2)+|(1)+(2)+)(6)+(3)+)($|(5)+|(0)+))|((((4)+|(1)+(4)+)(3)+|((4)+|(1)+(4)+)(6)+(3)+)($|(5)+|(0)+))");

    private Map<Integer,TreeSet<Integer>> conflitos = new HashMap<>();
    private Map<Integer,Integer> cicloDoVerdeConflitante = new HashMap<>();

    public MonitorDeFalhas(MotorEventoHandler motorEventoHandler) {
        this.motorEventoHandler = motorEventoHandler;
    }

    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

        if(!sequenciaCoresValida(intervalos)){
            motorEventoHandler.handle(new EventoMotor(timestamp,TipoEvento.FALHA_SEQUENCIA_DE_CORES,anel));
        }else if(verdesConflitantes(intervalos)){
            if(cicloDoVerdeConflitante.containsKey(anel)){
                motorEventoHandler.handle(new EventoMotor(timestamp,TipoEvento.FALHA_VERDES_CONFLITANTES,anel,false));
            }else{
                cicloDoVerdeConflitante.put(anel,3);
                motorEventoHandler.handle(new EventoMotor(timestamp,TipoEvento.FALHA_VERDES_CONFLITANTES,anel,true));
            }
        }
    }

    private boolean verdesConflitantes(IntervaloGrupoSemaforico intervalos) {
        return intervalos.getEstados().entrySet().stream().map(integerRangeMapEntry -> {
            if (conflitos.containsKey(integerRangeMapEntry.getKey())) {
                return conflita(integerRangeMapEntry.getValue(),conflitos.get(integerRangeMapEntry.getKey())
                                                                  .stream().map(integer -> intervalos.getEstados()
                                                                  .get(integer)).collect(Collectors.toList()));
            }else{
                return  false;
            }
        }).anyMatch(Boolean::booleanValue);
    }


    private boolean conflita(RangeMap<Long, EstadoGrupoSemaforico> origem, List<RangeMap<Long, EstadoGrupoSemaforico>> outros){

        return origem.asDescendingMapOfRanges().entrySet().stream().filter(rangeEstadoGrupoSemaforicoEntry ->
            rangeEstadoGrupoSemaforicoEntry.getValue().equals(EstadoGrupoSemaforico.VERDE)
        ).map(rangeEstadoGrupoSemaforicoEntry -> {
            for(long i = rangeEstadoGrupoSemaforicoEntry.getKey().lowerEndpoint(); i <  rangeEstadoGrupoSemaforicoEntry.getKey().upperEndpoint(); i+=100){
                long finalI = i;
                if(outros.stream().anyMatch(outro -> !outro.get(finalI).equals(EstadoGrupoSemaforico.VERMELHO))){
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
        if(!conflitos.containsKey(a)){
            conflitos.put(a, new TreeSet<>());
        }
        conflitos.get(a).add(b);
    }

    public void onClicloEnds(int anel, Long numeroCiclos) {
        if(cicloDoVerdeConflitante.containsKey(anel)){
            Integer ciclo = cicloDoVerdeConflitante.get(anel);
            cicloDoVerdeConflitante.put(anel,--ciclo > 0 ? ciclo :null );
        }
    }
}

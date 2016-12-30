package engine;

import com.google.common.collect.RangeMap;
import models.Detector;
import models.EstadoGrupoSemaforico;
import models.Plano;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 11/9/16.
 */
public class MonitorDeFalhas {


    private final Motor motor;

    private final Pattern sequenciaDeCores = Pattern.compile("(^(0)+$)|(^(3)+$)|(^(1)+$)|(^(5)+$)|(^(3)+(5)+$)|(^(3)+(0)+$)|(^(3)+(1)+$)|(^(5)+(3)+$)|(^(0)+(3)+$)|(^(0)+(5)+(3)+$)|(^((5)+(3)+|(0)+(5)+(3)+|(0)+(3)+)(1)+$)|((((2)+|(1)+(2)+)(3)+|((2)+|(1)+(2)+)(6)+(3)+)($|(5)+|(0)+))|((((4)+|(1)+(4)+)(3)+|((4)+|(1)+(4)+)(6)+(3)+)($|(5)+|(0)+))");

    private final MotorEventoHandler eventoHandler;

    private long ticks = 0L;

    private Map<Integer, TreeSet<Integer>> conflitos = new HashMap<>();

    private Map<Integer, Integer> cicloDoVerdeConflitante = new HashMap<>();

    private Map<Integer, Long> retiraVerdeConflitantes = new HashMap<>();

    private Map<Detector, Pair<Long, Long>> ausenciaDeteccao = new HashMap<>();

    private Map<Long, List<TipoEvento>> historico = new HashMap<>();

    private Map<Integer, Map<Long, List<TipoEvento>>> historicoAnel = new HashMap<>();

    private boolean[] aneisComFalha = new boolean[5];

    private boolean[] aneisComFalhaIrrecuperavel = new boolean[5];

    public MonitorDeFalhas(Motor motor, List<Detector> detectors) {
        this.motor = motor;
        this.eventoHandler = new MotorEventoHandler(motor);
        registraMonitoramentoDetectores(detectors);
    }


    public void tick(DateTime timestamp, List<Plano> planos) {
        ticks += 100L;

        monitoraDetectores(timestamp);

        for (Map.Entry<Integer, Long> entry : retiraVerdeConflitantes.entrySet()) {
            if (entry.getValue().equals(ticks)) {
                motor.onEvento(new EventoMotor(timestamp, TipoEvento.REMOCAO_FALHA_VERDES_CONFLITANTES, entry.getKey(), planos.get(entry.getKey() - 1)));
                retiraVerdeConflitantes.put(entry.getKey(), Long.MIN_VALUE);
            }
        }
    }

    private void monitoraDetectores(DateTime timestamp) {
        ausenciaDeteccao.entrySet().stream().forEach(entry -> {
            Pair<Long, Long> newValue = new Pair<Long, Long>(entry.getValue().getFirst(), entry.getValue().getSecond() + 100L);
            if (newValue.getSecond().equals(newValue.getFirst())) {
                TipoEvento tv = entry.getKey().isPedestre() ? TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO :
                    TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO;

                motor.onEvento(new EventoMotor(timestamp, tv,
                    new Pair<Integer, TipoDetector>(entry.getKey().getPosicao(), entry.getKey().getTipo()),
                    entry.getKey().getAnel().getPosicao()));

                newValue = new Pair<Long, Long>(entry.getValue().getFirst(), 0L);
            }
            ausenciaDeteccao.put(entry.getKey(), newValue);
        });
    }

    public void registraAcionamentoDetector(Detector detector) {
        if (ausenciaDeteccao.containsKey(detector)) {
            ausenciaDeteccao.put(detector, new Pair<Long, Long>(detector.getTempoAusenciaDeteccao() * 60000L, 0L));
        }
    }

    private void registraMonitoramentoDetectores(List<Detector> detectores) {
        detectores.stream().filter(detector -> detector.isMonitorado()).forEach(detector -> registraMonitoramentoDetector(detector));
    }

    private void registraMonitoramentoDetector(Detector detector) {
        ausenciaDeteccao.put(detector, new Pair<Long, Long>(detector.getTempoAusenciaDeteccao() * 60000L, 0L));
    }

    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

        if (cicloDoVerdeConflitante.containsKey(anel) && !retiraVerdeConflitantes.containsKey(anel)) {
            final long duracao = intervalos.getEntreverde() != null ? intervalos.getEntreverde().getDuracao() : 0L;
            retiraVerdeConflitantes.put(anel, ticks + duracao + 10000L);
        }

        if (!sequenciaCoresValida(intervalos)) {
            motor.onEvento(new EventoMotor(timestamp, TipoEvento.FALHA_SEQUENCIA_DE_CORES, anel, false));
        }

        if (verdesConflitantes(intervalos)) {
            motor.onEvento(new EventoMotor(timestamp, TipoEvento.FALHA_VERDES_CONFLITANTES, anel, false));
        }
    }

    public void monitoraRepeticaoVerdesConflitantes(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        if (!cicloDoVerdeConflitante.containsKey(anel)) {
            cicloDoVerdeConflitante.put(anel, 3);
        } else {
            aneisComFalhaIrrecuperavel[eventoMotor.getAnel()] = true;
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
                if (outros.stream().anyMatch(outro -> !outro.get(finalI).equals(EstadoGrupoSemaforico.VERMELHO) &&
                    !outro.get(finalI).equals(EstadoGrupoSemaforico.DESLIGADO) &&
                    !outro.get(finalI).equals(EstadoGrupoSemaforico.AMARELO_INTERMITENTE))) {
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
                retiraVerdeConflitantes.remove(anel);
            }

        }
    }

    public void handle(EventoMotor eventoMotor) {
        if (isEventoDuplicado(eventoMotor)) return;

        TipoEvento tipoEvento = eventoMotor.getTipoEvento();
        switch (tipoEvento.getTipoEventoControlador()) {
            case ALARME:
                motor.onAlarme(eventoMotor);
                eventoHandler.handle(eventoMotor);
                break;
            case FALHA:
                if (!isControladorEmFalha(eventoMotor.getAnel())) {
                    motor.onFalha(eventoMotor);
                    eventoHandler.handle(eventoMotor);

                    if (tipoEvento.isEntraEmIntermitente()) {
                        aneisComFalha[eventoMotor.getAnel()] = true;

                        if (tipoEvento.equals(TipoEvento.FALHA_VERDES_CONFLITANTES)) {
                            monitoraRepeticaoVerdesConflitantes(eventoMotor);
                        }
                    }
                }
                break;
            case REMOCAO_FALHA:
                if (!isControladorComFalhaIrrecuperavel(eventoMotor.getAnel())) {
                    if (tipoEvento.isEntraEmIntermitente()) {
                        aneisComFalha[eventoMotor.getAnel()] = false;
                    }
                    motor.onRemocaoFalha(eventoMotor);
                    eventoHandler.handle(eventoMotor);
                }
                break;
            default:
                eventoHandler.handle(eventoMotor);
                break;
        }
    }

    private boolean isEventoDuplicado(EventoMotor eventoMotor) {
        long ticks = this.ticks;

        if(eventoMotor.getAnel() > 0) {
            if (historicoAnel.get(eventoMotor.getAnel()) == null) {
                historicoAnel.put(eventoMotor.getAnel(), new HashMap<>());
            }
            if (verificaHistoricoEventos(eventoMotor, ticks, historicoAnel.get(eventoMotor.getAnel()))) { return true; }
        }else {
            if (verificaHistoricoEventos(eventoMotor, ticks, historico)) { return true; }
        }
        return false;
    }

    private boolean verificaHistoricoEventos(EventoMotor eventoMotor, long ticks, Map<Long, List<TipoEvento>> historico) {
        for (long t = ticks; t >= ticks - 1000L; t -= 100) {
            if (historico.get(t) != null && historico.get(t).contains(eventoMotor.getTipoEvento())) {
                return true;
            }
        }

        if (historico.get(ticks) == null) {
            historico.put(ticks, new ArrayList<>());
        }

        historico.get(ticks).add(eventoMotor.getTipoEvento());
        return false;
    }


    public void endTick() {
        historico.remove(ticks);
    }

    private boolean isControladorEmFalha(Integer anel) {
        return aneisComFalha[0] || aneisComFalha[anel];
    }

    public boolean isControladorComFalhaIrrecuperavel(Integer anel) {
        return aneisComFalhaIrrecuperavel[0] || aneisComFalhaIrrecuperavel[anel];
    }
}

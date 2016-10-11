package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Range.closedOpen;
import static engine.TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE;
import static tyrex.util.Configuration.console;

/**
 * Created by rodrigosol on 10/11/16.
 */
public class GerenciadorDeEstagios  implements EventoCallback{

    private final DateTime inicioControlador;

    private final DateTime inicioExecucao;

    private final List<EstagioPlano> listaEstagioPlanos;

    private final Plano plano;

    private final GerenciadorDeEstagiosCallback callback;

    private Long contador = 0L;

    private Long tempoDecorrido = 0L;

    private EstagioPlano estagioPlanoAtual;

    RangeMap<Long, EstagioPlano> intervalos;

    private Long numeroCiclos = 1L;

    private List<EstagioPlano> estagiosProximoCiclo = new ArrayList<>();

    public GerenciadorDeEstagios(DateTime inicioControlador, DateTime inicioExecucao, Plano plano, GerenciadorDeEstagiosCallback callback) {
        this.inicioControlador = inicioControlador;
        this.inicioExecucao = inicioExecucao;
        this.plano = plano;
        this.listaEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        this.callback = callback;

        geraIntervalos();
    }

    private void geraIntervalos() {
        this.intervalos = TreeRangeMap.create();
        Long inicio = 0L;
        Long tempoEstagio;
        for (EstagioPlano estagioPlano : listaEstagioPlanos){
            tempoEstagio = plano.getTempoEstagio(estagioPlano) * 1000L;
            this.intervalos.put(closedOpen(inicio, inicio + tempoEstagio), estagioPlano);
            inicio += tempoEstagio;
        }
    }

    public RangeMap<Long, EstagioPlano> getIntervalos() {
        return intervalos;
    }

    public List<Integer> getPosicaoEstagio(){
        return this.intervalos.asMapOfRanges().entrySet().stream().map(rangeEstagioPlanoEntry -> rangeEstagioPlanoEntry.getValue().getEstagio().getPosicao()).collect(Collectors.toList());
    }

    public Estagio tick(){
        EstagioPlano estagioPlano = this.intervalos.get(contador);
        contador += 1000L;
        if (this.intervalos.get(contador) == null){
            contador = 0L;
            numeroCiclos += 1;
            geraIntervalos();
            if(!estagiosProximoCiclo.isEmpty()){
                estagiosProximoCiclo.forEach(ep -> atualizaEstagiosPraFrente(ep));
            }
            estagiosProximoCiclo.clear();
        }

        if(!estagioPlano.equals(estagioPlanoAtual)){
            callback.onChangeEstagio(numeroCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido), estagioPlanoAtual, estagioPlano);
            estagioPlanoAtual = estagioPlano;
        }

        tempoDecorrido += 1000L;
        return estagioPlano.getEstagio();
    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
        switch (eventoMotor.getTipoEvento()) {
            case ACIONAMENTO_DETECTOR_PEDESTRE:
                processaDetectorPedestre(eventoMotor);
                break;
            case ACIONAMENTO_DETECTOR_VEICULAR:
                processarDetectorVeicular(eventoMotor);
                break;
        }

    }

    private void processarDetectorVeicular(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        Estagio estagio = detector.getEstagio();
        if(estagio.isDemandaPrioritaria()){
            atualizaEstagiosComDemandaPrioritaria(estagio);
        }
    }

    private void atualizaEstagiosComDemandaPrioritaria(Estagio estagio) {
        EstagioPlano estagioPlanoAnterior = estagioPlanoAtual.getEstagioPlanoAnterior(listaEstagioPlanos);
        EstagioPlano estagioPlanoProximo = estagioPlanoAtual.getEstagioPlanoProximo(listaEstagioPlanos);

        final long tempoEstagioAtual = intervalos.getEntry(contador).getKey().upperEndpoint() - intervalos.getEntry(contador).getKey().lowerEndpoint();
        final long tempoDecorridoNoEstagio = contador - intervalos.getEntry(contador).getKey().lowerEndpoint();
        final long tempoFaltanteVerde = Math.max((estagioPlanoAtual.getEstagio().getTempoMaximoVerdeSeguranca() + plano.getTempoEntreVerdeEntreEstagios(estagioPlanoAnterior.getEstagio(), estagioPlanoAtual.getEstagio())) * 1000 - tempoDecorridoNoEstagio, 0);
        final long tempoEntreVerdePrioritario = plano.getTempoEntreVerdeEntreEstagios(estagioPlanoAtual.getEstagio(), estagio) * 1000;
        final long tempoVerdeEstagioPrioritario = estagio.getTempoVerdeDemandaPrioritaria() * 1000;
        final long tempoEntreVerde = plano.getTempoEntreVerdeEntreEstagios(estagio, estagioPlanoProximo.getEstagio()) * 1000;

        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setEstagio(estagio);
        final long novoTerminoEstagioAtual = tempoDecorridoNoEstagio + tempoFaltanteVerde;
        final long tempoNovoEstagio = tempoEntreVerdePrioritario + tempoVerdeEstagioPrioritario;
        final long offset = tempoNovoEstagio - (tempoEstagioAtual - novoTerminoEstagioAtual);
        this.intervalos = mergeIntervalos(offset, tempoNovoEstagio, estagioPlano, estagioPlanoProximo.getPosicao(), novoTerminoEstagioAtual);
    }

    private void processaDetectorPedestre(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        EstagioPlano estagioPlano = plano.getEstagiosPlanos()
                .stream()
                .filter(EstagioPlano::isDispensavel)
                .filter(estagioPlano1 -> estagioPlano1.getEstagio().equals(detector.getEstagio()))
                .findFirst()
                .orElse(null);
        int compare = estagioPlano.getPosicao().compareTo(estagioPlanoAtual.getPosicao());
        if (compare < 0){
            //Proximo ciclo
            System.out.println("Proximo ciclo");
            estagiosProximoCiclo.add(estagioPlano);
        } else if (compare > 0) {
            //Ciclo atual
            System.out.println("Ciclo Atual");
            atualizaEstagiosPraFrente(estagioPlano);
        }
    }

    private void atualizaEstagiosPraFrente(EstagioPlano estagioPlano) {
        final long offset = plano.getTempoEstagio(estagioPlano) * 1000L;
        Range<Long> range = this.intervalos.getEntry(contador).getKey();
        this.intervalos = mergeIntervalos(offset, offset, estagioPlano, estagioPlano.getPosicao(), range.upperEndpoint());
    }

    public RangeMap<Long, EstagioPlano> mergeIntervalos(final long offset, final long tempoNovoEstagio, final EstagioPlano estagioPlano, final int posicaoCorte, final long tempoFinalEstagioAtual) {
        Range<Long> range = this.intervalos.getEntry(contador).getKey();
        List<Map.Entry<Range<Long>, EstagioPlano>> head = intervalos.asMapOfRanges().entrySet().stream().filter(entry -> {
            return entry.getValue().getPosicao() < posicaoCorte;
        }).collect(Collectors.toList());

        List<Map.Entry<Range<Long>, EstagioPlano>> tail = intervalos.asMapOfRanges().entrySet().stream().filter(entry -> {
            return entry.getKey().lowerEndpoint() >= range.upperEndpoint() && entry.getValue().getPosicao() >= posicaoCorte;
        }).collect(Collectors.toList());

        RangeMap<Long, EstagioPlano> novosIntervalos = TreeRangeMap.create();

        final Long[] tempo = {0L};
        head.forEach(entry -> {
            if (entry.getKey().equals(range)){
                novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint(), tempoFinalEstagioAtual), entry.getValue());
                tempo[0] = tempoFinalEstagioAtual;
            } else {
                novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint(), entry.getKey().upperEndpoint()), entry.getValue());
                tempo[0] = entry.getKey().upperEndpoint();
            }
        });

        Range<Long> novoRange = Range.closedOpen(tempo[0], tempo[0] + tempoNovoEstagio);

        novosIntervalos.put(novoRange, estagioPlano);

        tail.forEach(entry -> {
            novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint() + offset, entry.getKey().upperEndpoint() + offset), entry.getValue());
        });

        return novosIntervalos;
    }
}

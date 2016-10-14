package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.Detector;
import models.Estagio;
import models.EstagioPlano;
import models.Plano;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 10/11/16.
 */
public class GerenciadorDeEstagios implements EventoCallback {

    private final DateTime inicioControlador;

    private final DateTime inicioExecucao;

    private final HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde;

    private final List<EstagioPlano> listaOriginalEstagioPlanos;

    private final Plano plano;

    private final GerenciadorDeEstagiosCallback callback;

    RangeMap<Long, Intervalo> intervalos;

    private List<EstagioPlano> listaEstagioPlanos;

    private Long tempoDecorrido = 0L;

    private EstagioPlano estagioPlanoAtual;

    private EstagioPlano estagioPlanoAnterior;

    private Long numeroCiclos = 1L;

    private List<EstagioPlano> estagiosProximoCiclo = new ArrayList<>();

    private long contadorIntervalo = 0L;

    private int contadorEstagio = 0;

    public GerenciadorDeEstagios(DateTime inicioControlador, DateTime inicioExecucao, Plano plano, GerenciadorDeEstagiosCallback callback) {
        this.inicioControlador = inicioControlador;
        this.inicioExecucao = inicioExecucao;
        this.plano = plano;
        this.listaOriginalEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        this.listaEstagioPlanos = new ArrayList<>(listaOriginalEstagioPlanos);
        this.estagioPlanoAtual = listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
        this.tabelaDeTemposEntreVerde = this.plano.tabelaEntreVerde();
        this.callback = callback;

        contadorEstagio = 0;
        geraIntervalos(0);
    }

    private void geraIntervalos(Integer index) {
        System.out.println(listaEstagioPlanos);
        this.intervalos = TreeRangeMap.create();

        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);
        Estagio estagioAtual = estagioPlano.getEstagio();
        final Estagio estagioAnterior = estagioPlanoAtual.getEstagio();

        final long tempoEntreVerde = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));
        final long tempoVerde = estagioPlano.getTempoVerdeEstagio() * 1000L;

        this.intervalos.put(Range.closedOpen(0L, tempoEntreVerde), new Intervalo(tempoEntreVerde, true, estagioPlano));
        this.intervalos.put(Range.closedOpen(tempoEntreVerde, tempoEntreVerde + tempoVerde), new Intervalo(tempoVerde, false, estagioPlano));
    }

    public RangeMap<Long, Intervalo> getIntervalos() {
        return intervalos;
    }

    public List<Integer> getPosicaoEstagio() {
        return this.intervalos.asMapOfRanges().entrySet().stream().map(rangeEstagioPlanoEntry -> rangeEstagioPlanoEntry.getValue().getEstagio().getPosicao()).collect(Collectors.toList());
    }

    public Estagio tick() {
        Intervalo intervalo = this.intervalos.get(contadorIntervalo);

        if (this.intervalos.get(contadorIntervalo) == null) {
            contadorIntervalo = 0L;
            contadorEstagio++;
            if (contadorEstagio == listaEstagioPlanos.size()) {
                atualizaListaEstagiosNovoCiclo(listaOriginalEstagioPlanos);
                contadorEstagio = 0;
                geraIntervalos(0);
            } else {
                geraIntervalos(contadorEstagio);
            }
            intervalo = this.intervalos.get(contadorIntervalo);
        }

        EstagioPlano estagioPlano = intervalo.getEstagioPlano();
        if (!estagioPlano.equals(estagioPlanoAtual)) {
            callback.onChangeEstagio(numeroCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido), estagioPlanoAtual, estagioPlano);
            estagioPlanoAnterior = estagioPlanoAtual;
            estagioPlanoAtual = estagioPlano;
        }

        contadorIntervalo += 100L;
        tempoDecorrido += 100L;
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
        if (estagio.isDemandaPrioritaria() && !estagioPlanoAtual.getEstagio().equals(estagio)) {
            boolean proximoEstagio = false;
            if (contadorEstagio + 1 < listaEstagioPlanos.size()) {
                proximoEstagio = listaEstagioPlanos.get(contadorEstagio + 1).getEstagio().equals(estagio);
            }
            if (!proximoEstagio) {
                reduzirTempoEstagioAtual(estagioPlanoAnterior);
                adicionaEstagioDemandaPrioritaria(estagio);
            }
        } else if (plano.isAtuado()) {
            atualizaEstagiosAtuado(estagio);
        }
    }

    private void reduzirTempoEstagioAtual(EstagioPlano estagioPlanoAnterior) {
        Map.Entry<Range<Long>, Intervalo> range = this.intervalos.getEntry(contadorIntervalo);
        Intervalo intervalo = range.getValue();
        if (intervalo.isEntreverde()) {
            range = this.intervalos.getEntry(range.getKey().upperEndpoint() + 1);
            intervalo = range.getValue();
            intervalo.setDuracao(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior, 0));
        } else {
            intervalo.setDuracao(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior, contadorIntervalo));
        }

        this.intervalos.remove(range.getKey());
        this.intervalos.put(Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + intervalo.getDuracao()), intervalo);
    }

    private void aumentarTempoEstagioAtual(EstagioPlano estagioPlano) {
        Map.Entry<Range<Long>, Intervalo> range = this.intervalos.getEntry(contadorIntervalo);
        final long tempoExtensao = ((long) (estagioPlano.getTempoExtensaoVerde() * 1000L));
        final long tempoMaximo = estagioPlano.getTempoVerdeMaximo() * 1000L;

        if ((range.getKey().upperEndpoint() - range.getKey().lowerEndpoint()) + tempoExtensao <= tempoMaximo) {
            this.intervalos.remove(range.getKey());
            this.intervalos.put(Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().upperEndpoint() + tempoExtensao), range.getValue());
        }
    }

    private void adicionaEstagioDemandaPrioritaria(Estagio estagio) {
        if (!listaEstagioPlanos.stream().anyMatch(estagioPlano -> estagioPlano.getEstagio().equals(estagio))) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setEstagio(estagio);
            estagioPlano.setPlano(plano);
            estagioPlano.setTempoVerde(estagio.getTempoVerdeDemandaPrioritaria());
            listaEstagioPlanos.add(listaEstagioPlanos.indexOf(estagioPlanoAtual) + 1, estagioPlano);
        }
    }

    private void atualizaEstagiosAtuado(Estagio estagio) {
        EstagioPlano estagioPlano = plano.getEstagiosPlanos()
                .stream()
                .filter(estagioPlano1 -> estagioPlano1.getEstagio().equals(estagio))
                .findFirst()
                .orElse(null);

        Intervalo intervalo = this.intervalos.get(contadorIntervalo);
        if (!intervalo.isEntreverde() && estagioPlanoAtual.equals(estagioPlano)) {
            aumentarTempoEstagioAtual(estagioPlano);
        }
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
        if (compare < 0) {
            System.out.println("Proximo ciclo: " + estagioPlanoAtual);
            if (!estagiosProximoCiclo.contains(estagioPlano)) {
                estagiosProximoCiclo.add(estagioPlano);
            }
        } else if (compare > 0) {
            System.out.println("Ciclo Atual: " + estagioPlanoAtual);
            if (!listaEstagioPlanos.contains(estagioPlano)) {
                atualizaEstagiosCicloAtual(estagioPlano);
            }
        }
    }
//
//    private void atualizaEstagiosComDemandaPrioritaria(Estagio estagio) {
//        EstagioPlano estagioPlanoAnterior = estagioPlanoAtual.getEstagioPlanoAnterior(listaEstagioPlanos);
//        EstagioPlano estagioPlanoProximo = estagioPlanoAtual.getEstagioPlanoProximo(listaEstagioPlanos);
//
//        final long tempoEstagioAtual = intervalos.getEntry(contador).getKey().upperEndpoint() - intervalos.getEntry(contador).getKey().lowerEndpoint();
//        final long tempoDecorridoNoEstagio = contador - intervalos.getEntry(contador).getKey().lowerEndpoint();
//        final long tempoFaltanteVerde = Math.max((estagioPlanoAtual.getEstagio().getTempoMaximoVerdeSeguranca() + plano.getTempoEntreVerdeEntreEstagios(estagioPlanoAnterior.getEstagio(), estagioPlanoAtual.getEstagio())) * 1000 - tempoDecorridoNoEstagio, 0);
//        final long tempoEntreVerdePrioritario = plano.getTempoEntreVerdeEntreEstagios(estagioPlanoAtual.getEstagio(), estagio) * 1000;
//        final long tempoVerdeEstagioPrioritario = estagio.getTempoVerdeDemandaPrioritaria() * 1000;
//        final long tempoEntreVerde = plano.getTempoEntreVerdeEntreEstagios(estagio, estagioPlanoProximo.getEstagio()) * 1000;
//
//        EstagioPlano estagioPlano = new EstagioPlano();
//        estagioPlano.setEstagio(estagio);
//        final long novoTerminoEstagioAtual = tempoDecorridoNoEstagio + tempoFaltanteVerde;
//        final long tempoNovoEstagio = tempoEntreVerdePrioritario + tempoVerdeEstagioPrioritario;
//        final long offset = tempoNovoEstagio - (tempoEstagioAtual - novoTerminoEstagioAtual);
////        this.intervalos = mergeIntervalos(offset, tempoNovoEstagio, estagioPlano, estagioPlanoProximo.getPosicao(), novoTerminoEstagioAtual);
//    }

    private void atualizaEstagiosCicloAtual(EstagioPlano estagioPlano) {
        List<EstagioPlano> novaLista = new ArrayList<>();
        final Boolean[] adicionado = {false};
        listaEstagioPlanos.forEach(item -> {
            if (!(item.getPosicao() < estagioPlano.getPosicao()) && !adicionado[0]) {
                novaLista.add(estagioPlano);
                adicionado[0] = true;
            }
            novaLista.add(item);
        });
        if (novaLista.size() == listaEstagioPlanos.size()) {
            novaLista.add(estagioPlano);
        }
        listaEstagioPlanos = novaLista;
    }

    private void atualizaListaEstagiosNovoCiclo(List<EstagioPlano> lista) {
        this.listaEstagioPlanos = new ArrayList<>(lista);
        estagiosProximoCiclo.forEach(estagioPlano -> {
            atualizaEstagiosCicloAtual(estagioPlano);
        });
        estagiosProximoCiclo.clear();
    }

    public static class Intervalo {

        private long duracao;

        private boolean entreverde = false;

        private EstagioPlano estagioPlano;

        public Intervalo(long duracao, boolean entreverde, EstagioPlano estagioPlano) {
            this.duracao = duracao;
            this.entreverde = entreverde;
            this.estagioPlano = estagioPlano;
        }

        public long getDuracao() {
            return duracao;
        }

        public void setDuracao(long duracao) {
            this.duracao = duracao;
        }

        public boolean isEntreverde() {
            return entreverde;
        }

        public void setEntreverde(boolean entreverde) {
            this.entreverde = entreverde;
        }

        public EstagioPlano getEstagioPlano() {
            return estagioPlano;
        }

        public void setEstagioPlano(EstagioPlano estagioPlano) {
            this.estagioPlano = estagioPlano;
        }

        public Estagio getEstagio() {
            return this.estagioPlano.getEstagio();
        }

        @Override
        public String toString() {
            return "Intervalo{" +
                    "duracao=" + duracao +
                    ", entreverde=" + entreverde +
                    ", estagioPlano=" + estagioPlano +
                    '}';
        }
    }

//    private void atualizaEstagiosProximoCiclo(EstagioPlano estagioPlano) {
//        List<EstagioPlano> novaLista = new ArrayList<>(listaEstagioPlanos);
//        final Boolean[] adicionado = {false};
//        listaEstagioPlanos.forEach(item -> {
//            if (!(item.getPosicao() < estagioPlano.getPosicao()) && !adicionado[0]) {
//                novaLista.add(estagioPlano);
//                adicionado[0] = true;
//            }
//            novaLista.add(item);
//        });
//        listaEstagioPlanos = novaLista;
//    }

//    public RangeMap<Long, EstagioPlano> mergeIntervalos(final long offset, final long tempoNovoEstagio, final EstagioPlano estagioPlano, final int posicaoCorte, final long tempoFinalEstagioAtual, final long tempoFinalEstagioProximo) {
//        Range<Long> range = this.intervalos.getEntry(contador).getKey();
//        List<Map.Entry<Range<Long>, EstagioPlano>> head = intervalos.asMapOfRanges().entrySet().stream().filter(entry -> {
//            return entry.getValue().getPosicao() < posicaoCorte;
//        }).collect(Collectors.toList());
//
//        List<Map.Entry<Range<Long>, EstagioPlano>> tail = intervalos.asMapOfRanges().entrySet().stream().filter(entry -> {
//            return entry.getKey().lowerEndpoint() >= range.upperEndpoint() && entry.getValue().getPosicao() >= posicaoCorte;
//        }).collect(Collectors.toList());
//
//        RangeMap<Long, EstagioPlano> novosIntervalos = TreeRangeMap.create();
//
//        final Long[] tempo = {0L};
//        head.forEach(entry -> {
//            if (entry.getKey().equals(range)){
//                novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint(), tempoFinalEstagioAtual), entry.getValue());
//                tempo[0] = tempoFinalEstagioAtual;
//            } else {
//                novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint(), entry.getKey().upperEndpoint()), entry.getValue());
//                tempo[0] = entry.getKey().upperEndpoint();
//            }
//        });
//
//        Range<Long> novoRange = Range.closedOpen(tempo[0], tempo[0] + tempoNovoEstagio);
//
//        novosIntervalos.put(novoRange, estagioPlano);
//
//        tail.forEach(entry -> {
//            novosIntervalos.put(Range.closedOpen(entry.getKey().lowerEndpoint() + offset, entry.getKey().upperEndpoint() + offset), entry.getValue());
//        });
//
//        return novosIntervalos;
//    }
}

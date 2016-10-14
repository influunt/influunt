package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;
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

        this.intervalos.put(Range.closedOpen(0L, tempoEntreVerde), new Intervalo(tempoEntreVerde, true, estagioPlano, estagioPlanoAtual));
        this.intervalos.put(Range.closedOpen(tempoEntreVerde, tempoEntreVerde + tempoVerde), new Intervalo(tempoVerde, false, estagioPlano, estagioPlanoAtual));
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
        }
        intervalo.setDuracao(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior));
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

        private EstagioPlano estagioPlanoAnterior;

        public Intervalo(long duracao, boolean entreverde, EstagioPlano estagioPlano, EstagioPlano estagioPlanoAnterior) {
            this.duracao = duracao;
            this.entreverde = entreverde;
            this.estagioPlano = estagioPlano;
            this.estagioPlanoAnterior = estagioPlanoAnterior;
            loadEstados();
        }

        private void loadEstados() {
            final Estagio estagio = estagioPlano.getEstagio();
            final Plano plano = estagioPlano.getPlano();
            estados = new HashMap<>();
            estagio.getGruposSemaforicos().forEach(grupoSemaforico -> {
                estados.put(grupoSemaforico.getPosicao(), loadGrupoSemaforico(grupoSemaforico));
            });

            plano.getGruposSemaforicosPlanos().stream()
                    .filter(grupoSemaforicoPlano -> !estagio.getGruposSemaforicos().contains(grupoSemaforicoPlano.getGrupoSemaforico()))
                    .forEach(grupoSemaforicoPlano -> {
                        estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), loadGrupoSemaforico(grupoSemaforicoPlano));
                    });

        }

        //Ganho direito de passagem
        private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
            RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();
            if (entreverde) {
                final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
                if(estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
                    intervalos.put(Range.closedOpen(0L, duracao), EstadoGrupoSemaforico.VERDE);
                } else {
                    final Estagio estagioAtual = estagioPlano.getEstagio();
                    final Transicao transicao = grupoSemaforico.findTransicaoComGanhoDePassagemByOrigemDestino(estagioAnterior, estagioAtual);
                    final long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;
                    intervalos.put(Range.closedOpen(0L, tempoAtraso), EstadoGrupoSemaforico.VERDE);
                    intervalos.put(Range.closedOpen(tempoAtraso, duracao), EstadoGrupoSemaforico.VERMELHO);
                }
            } else {
                intervalos.put(Range.closedOpen(0L, duracao), EstadoGrupoSemaforico.VERDE);
            }
            return intervalos;
        }

        //Perdendo o direito de passagem
        private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano) {
            RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();
            if(entreverde){
                final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
                final GrupoSemaforico grupoSemaforico = grupoSemaforicoPlano.getGrupoSemaforico();
                if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
                    final Plano plano = estagioPlano.getPlano();
                    final Estagio estagioAtual = estagioPlano.getEstagio();
                    final Transicao transicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAnterior, estagioAtual);
                    final TabelaEntreVerdesTransicao tabelaEntreVerdes = grupoSemaforico.findTabelaEntreVerdesTransicaoByTransicao(plano.getPosicaoTabelaEntreVerde(), transicao);
                    final long tempo;
                    final long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;

                    intervalos.put(Range.closedOpen(0L, tempoAtraso), EstadoGrupoSemaforico.VERDE);
                    if (grupoSemaforico.isPedestre()) {
                        tempo = tabelaEntreVerdes.getTempoVermelhoIntermitente() + tempoAtraso;
                        intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.VERMELHO_INTERMITENTE);
                    } else {
                        tempo = tabelaEntreVerdes.getTempoAmarelo() + tempoAtraso;
                        intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.AMARELO);
                    }
                    intervalos.put(Range.closedOpen(tempo, duracao), EstadoGrupoSemaforico.VERMELHO_LIMPEZA);
                } else {
                    intervalos.put(Range.closedOpen(0L, duracao), EstadoGrupoSemaforico.VERMELHO);
                }
            } else {
                intervalos.put(Range.closedOpen(0L, duracao), EstadoGrupoSemaforico.VERMELHO);
            }

            return intervalos;
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

        private HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> estados;

        @Override
        public String toString() {
            return "Intervalo{" +
                    "duracao=" + duracao +
                    ", entreverde=" + entreverde +
                    ", estagioPlano=" + estagioPlano +
                    '}';
        }

        public List<EstadoGrupoSemaforico> getEstadoGrupoSemaforicos(long instante){
            return estados.keySet()
                    .stream()
                    .sorted()
                    .map(i -> estados.get(i).get(instante))
                    .collect(Collectors.toList());
        }
    }

}

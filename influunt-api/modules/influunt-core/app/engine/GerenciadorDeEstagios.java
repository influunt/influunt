package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;

/**
 * Created by rodrigosol on 10/11/16.
 */
public class GerenciadorDeEstagios implements EventoCallback {

    private final DateTime inicioControlador;

    private final DateTime inicioExecucao;

    private HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde;

    private List<EstagioPlano> listaOriginalEstagioPlanos;

    private Plano plano;

    private final GerenciadorDeEstagiosCallback callback;

    private final int anel;

    RangeMap<Long, IntervaloEstagio> intervalos;

    private List<EstagioPlano> listaEstagioPlanos;

    private Long tempoDecorrido = 0L;

    private EstagioPlano estagioPlanoAtual;

    private EstagioPlano estagioPlanoAnterior;

    private List<EstagioPlano> estagiosProximoCiclo = new ArrayList<>();

    private long contadorIntervalo = 0L;

    private int contadorEstagio = 0;

    private long contadorDeCiclos = 0L;

    private GetIntervaloGrupoSemaforico intervaloGrupoSemaforicoAtual = null;
    private AgendamentoTrocaPlano agendamento = null;

    private String idJsonNovoEstagio;

    public GerenciadorDeEstagios(int anel,
                                 DateTime inicioControlador,
                                 DateTime inicioExecucao,
                                 Plano plano,
                                 GerenciadorDeEstagiosCallback callback) {

        this.anel = anel;
        this.inicioControlador = inicioControlador;
        this.inicioExecucao = inicioExecucao;
        this.callback = callback;

        reconhecePlano(plano);

    }

    public Estagio tick() {
        IntervaloEstagio intervalo = this.intervalos.get(contadorIntervalo);

        if (this.intervalos.get(contadorIntervalo) == null) {
            contadorIntervalo = 0L;
            contadorEstagio++;
            if (contadorEstagio == listaEstagioPlanos.size()) {
                atualizaListaEstagiosNovoCiclo(listaOriginalEstagioPlanos);
                contadorEstagio = 0;
                geraIntervalos(0);
                contadorDeCiclos++;
                callback.onCicloEnds(this.anel, contadorDeCiclos);
                if(this.agendamento != null){
                    //Calcular entreverdes
                    agendamento.setMomentoDaTroca(tempoDecorrido);
                    callback.onTrocaDePlanoEfetiva(agendamento);
                    reconhecePlano(this.agendamento.getPlano());
                    this.agendamento = null;
                }
            } else {
                geraIntervalos(contadorEstagio);
            }
            intervalo = this.intervalos.get(contadorIntervalo);
        }

        EstagioPlano estagioPlano = intervalo.getEstagioPlano();
        if (!estagioPlano.equals(estagioPlanoAtual)) {
            if (intervaloGrupoSemaforicoAtual != null) {
                IntervaloGrupoSemaforico intervaloGrupoSemaforico = new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde());
                callback.onEstagioEnds(this.anel, contadorDeCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido), intervaloGrupoSemaforico);
            }

            intervaloGrupoSemaforicoAtual = new GetIntervaloGrupoSemaforico().invoke();
            callback.onEstagioChange(this.anel, contadorDeCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido),
                    new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde()));

            estagioPlanoAnterior = estagioPlanoAtual;
            estagioPlanoAtual = estagioPlano;
        }

        contadorIntervalo += 100L;
        tempoDecorrido += 100L;
        return estagioPlano.getEstagio();
    }

    public void trocarPlano(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        agendamentoTrocaPlano.setMomentoPedidoTroca(tempoDecorrido);
        agendamentoTrocaPlano.setAnel(anel);
        agendamento = agendamentoTrocaPlano;
    }


    private void reconhecePlano(Plano plano) {

        this.plano = plano;
        this.tabelaDeTemposEntreVerde = this.plano.tabelaEntreVerde();
        this.listaOriginalEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        this.listaEstagioPlanos = new ArrayList<>(listaOriginalEstagioPlanos);
        contadorEstagio = 0;
        contadorIntervalo = 0l;
        contadorDeCiclos = 0l;
        tempoDecorrido = 0l;

        if (plano.isModoOperacaoVerde()) {
            this.estagioPlanoAtual = listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
            geraIntervalos(0);
        } else {
            geraIntervalosFixos();
        }
    }

    private void geraIntervalos(Integer index) {
        if (!plano.isModoOperacaoVerde() && index == 0){
            geraIntervalosFixos();
        } else {
            EstagioPlano estagioPlano = listaEstagioPlanos.get(index);
            Estagio estagioAtual = estagioPlano.getEstagio();
            final Estagio estagioAnterior = estagioPlanoAtual.getEstagio();

            final long tempoEntreVerde = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));
            final long tempoVerde = estagioPlano.getTempoVerdeEstagioComTempoDoEstagioDispensavel(tabelaDeTemposEntreVerde, listaEstagioPlanos) * 1000L;

            this.intervalos = TreeRangeMap.create();
            this.intervalos.put(Range.closedOpen(0L, tempoEntreVerde), new IntervaloEstagio(tempoEntreVerde, true, estagioPlano, estagioPlanoAtual));
            this.intervalos.put(Range.closedOpen(tempoEntreVerde, tempoEntreVerde + tempoVerde), new IntervaloEstagio(tempoVerde, false, estagioPlano, estagioPlanoAtual));
        }
    }

    private void geraIntervalosFixos(){
        Estagio estagio = new Estagio();
        EstagioPlano estagioPlano = new EstagioPlano();
        idJsonNovoEstagio = UUID.randomUUID().toString();
        estagioPlano.setIdJson(idJsonNovoEstagio);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(estagio);
        listaEstagioPlanos.add(estagioPlano);

        this.intervalos = TreeRangeMap.create();
        this.intervalos.put(Range.closedOpen(0L, 255000L), new IntervaloEstagio(255000L, false, estagioPlano, null));
    }

    public RangeMap<Long, IntervaloEstagio> getIntervalos() {
        return intervalos;
    }



    @Override
    public void onEvento(EventoMotor eventoMotor) {
            long offset = 0;
            if(this.intervalos.get(contadorIntervalo)==null){
                offset = contadorIntervalo - 1;
            }
            this.intervalos.get(offset).addEvento(contadorIntervalo,eventoMotor);
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
        final long contador;
        Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
        IntervaloEstagio intervalo = range.getValue();
        if (intervalo.isEntreverde()) {
            range = this.intervalos.getEntry(range.getKey().upperEndpoint() + 1);
            intervalo = range.getValue();
            contador = 0L;
        } else {
            contador = contadorIntervalo - range.getKey().lowerEndpoint();
        }
        long duracao = Math.max(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior), contador);
        intervalo.setDuracao(duracao);
        this.intervalos.remove(range.getKey());
        final Range<Long> novoRange = Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + duracao);
        this.intervalos.put(novoRange, intervalo);
    }

    private void aumentarTempoEstagioAtual(EstagioPlano estagioPlano) {
        Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
        final long tempoExtensao = ((long) (estagioPlano.getTempoExtensaoVerde() * 1000L));
        final long tempoMaximo = estagioPlano.getTempoVerdeMaximo() * 1000L;

        IntervaloEstagio intervalo = range.getValue();
        if ((intervalo.getDuracao() + tempoExtensao) <= tempoMaximo) {
            this.intervalos.remove(range.getKey());
            intervalo.setDuracao(intervalo.getDuracao() + tempoExtensao);
            this.intervalos.put(Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + intervalo.getDuracao()), intervalo);
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

        IntervaloEstagio intervalo = this.intervalos.get(contadorIntervalo);
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


    public int getAnel() {
        return anel;
    }

    private class GetIntervaloGrupoSemaforico {
        private IntervaloEstagio intervaloEntreverde;

        private IntervaloEstagio intervaloVerde;

        public IntervaloEstagio getIntervaloEntreverde() {
            return intervaloEntreverde;
        }

        public IntervaloEstagio getIntervaloVerde() {
            return intervaloVerde;
        }

        public GetIntervaloGrupoSemaforico invoke() {
            Map.Entry<Range<Long>, IntervaloEstagio> intervaloFirst = GerenciadorDeEstagios.this.intervalos.getEntry(0L);
            intervaloEntreverde = intervaloFirst.getValue();
            intervaloVerde = null;
            if (GerenciadorDeEstagios.this.intervalos.getEntry(intervaloFirst.getKey().upperEndpoint() + 1) != null) {
                intervaloVerde = GerenciadorDeEstagios.this.intervalos.getEntry(intervaloFirst.getKey().upperEndpoint() + 1).getValue();
            }
            if (intervaloVerde == null) {
                intervaloVerde = intervaloEntreverde;
                intervaloEntreverde = null;
            }
            return this;
        }
    }
}

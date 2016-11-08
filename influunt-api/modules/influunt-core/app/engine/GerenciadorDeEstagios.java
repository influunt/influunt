package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import engine.eventos.GerenciadorDeEventos;
import engine.intervalos.GeradorDeIntervalos;
import models.Estagio;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 10/11/16.
 */
public class GerenciadorDeEstagios implements EventoCallback {

    private final DateTime inicioControlador;

    private final DateTime inicioExecucao;

    private HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde;

    private List<EstagioPlano> listaOriginalEstagioPlanos;

    private Plano plano;

    private ModoOperacaoPlano modoAnterior;

    private final GerenciadorDeEstagiosCallback callback;

    private final int anel;

    private RangeMap<Long, IntervaloEstagio> intervalos;

    private List<EstagioPlano> listaEstagioPlanos;

    private Long tempoDecorrido = 0L;

    private EstagioPlano estagioPlanoAtual;

    private EstagioPlano estagioPlanoAnterior;

    private final List<EstagioPlano> estagiosProximoCiclo = new ArrayList<>();

    private long contadorIntervalo = 0L;

    private int contadorEstagio = 0;

    private long contadorDeCiclos = 0L;

    private GetIntervaloGrupoSemaforico intervaloGrupoSemaforicoAtual = null;

    private AgendamentoTrocaPlano agendamento = null;

    public GerenciadorDeEstagios(int anel,
                                 DateTime inicioControlador,
                                 DateTime inicioExecucao,
                                 Plano plano,
                                 GerenciadorDeEstagiosCallback callback) {

        this.anel = anel;
        this.inicioControlador = inicioControlador;
        this.inicioExecucao = inicioExecucao;
        this.callback = callback;

        reconhecePlano(plano, true);

    }

    public void tick() {
        IntervaloEstagio intervalo = this.intervalos.get(contadorIntervalo);

        //TODO: Se o intermitente sair antes de terminar o entreverde do estágio anterior o que deve acontecer?
        if (this.agendamento != null && (this.plano.isIntermitente() || this.plano.isApagada()) && !intervalo.isEntreverde()) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
            intervalo.setDuracao(contadorIntervalo - range.getKey().lowerEndpoint());
            executaAgendamentoTrocaDePlano();
            intervalo = this.intervalos.get(contadorIntervalo);
        } else {
            intervalo = verificaETrocaIntervalo(intervalo);
        }

        verificaETrocaEstagio(intervalo);

        verificaTempoMaximoDePermanenciaDoEstagio();

        contadorIntervalo += 100L;
        tempoDecorrido += 100L;
    }

    private IntervaloEstagio verificaETrocaIntervalo(IntervaloEstagio intervalo) {
        if (this.intervalos.get(contadorIntervalo) == null) {
            contadorIntervalo = 0L;
            contadorEstagio++;
            if (contadorEstagio == listaEstagioPlanos.size()) {
                atualizaListaEstagiosNovoCiclo(listaOriginalEstagioPlanos);
                contadorEstagio = 0;
                verificaEAjustaIntermitenteCasoDemandaPrioritaria();
                geraIntervalos(0);
                contadorDeCiclos++;
                callback.onCicloEnds(this.anel, contadorDeCiclos);
                if (this.agendamento != null && !this.plano.isManual()) {
                    executaAgendamentoTrocaDePlano();
                }
            } else {
                geraIntervalos(contadorEstagio);
            }
            intervalo = this.intervalos.get(contadorIntervalo);
        }
        return intervalo;
    }

    private void verificaEAjustaIntermitenteCasoDemandaPrioritaria() {
        if(!this.plano.isModoOperacaoVerde() &&
            estagioPlanoAtual.getEstagio().isDemandaPrioritaria()) {
            this.modoAnterior = ModoOperacaoPlano.TEMPO_FIXO_ISOLADO;
        }
    }

    private void verificaETrocaEstagio(IntervaloEstagio intervalo) {
        EstagioPlano estagioPlano = intervalo.getEstagioPlano();
        if (!estagioPlano.equals(estagioPlanoAtual) || planoComEstagioUnico()) {
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
    }

    private void verificaTempoMaximoDePermanenciaDoEstagio() {
        Estagio estagio = estagioPlanoAtual.getEstagio();
        if (estagio.isTempoMaximoPermanenciaAtivado()) {
            long tempoMaximoEstagio = estagio.getTempoMaximoPermanencia() * 1000L;
            if (intervaloGrupoSemaforicoAtual != null && intervaloGrupoSemaforicoAtual.getIntervaloEntreverde() != null) {
                tempoMaximoEstagio += intervaloGrupoSemaforicoAtual.getIntervaloEntreverde().getDuracao();
            }
            if (contadorIntervalo >= tempoMaximoEstagio) {
                if (plano.isManual()) {
                    this.agendamento.setMomentoOriginal(DateTime.now());
                    executaAgendamentoTrocaDePlano();
                } else {
                    //TODO: Deve gerar um erro de máximo permanencia
                }
            }
        }
    }

    private boolean planoComEstagioUnico() {
        return this.plano.isModoOperacaoVerde() && this.listaEstagioPlanos.size() == 1 && contadorIntervalo == 0L;
    }

    private void executaAgendamentoTrocaDePlano() {
        agendamento.setMomentoDaTroca(tempoDecorrido);
        callback.onTrocaDePlanoEfetiva(agendamento);
        reconhecePlano(this.agendamento.getPlano());
        if (this.agendamento.getPlano().isManual()) {
            this.agendamento = null;
            agendarTrocaPlano(getEstagioPlanoAnterior().getPlano());
        } else {
            this.agendamento = null;
        }

    }

    public void trocarPlano(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        agendamentoTrocaPlano.setMomentoPedidoTroca(tempoDecorrido);
        agendamentoTrocaPlano.setAnel(anel);
        agendamento = agendamentoTrocaPlano;
    }

    private void reconhecePlano(Plano plano) {
        reconhecePlano(plano, false);
    }

    private void agendarTrocaPlano(Plano plano){
        trocarPlano(new AgendamentoTrocaPlano(null, plano, null));
    }

    private void reconhecePlano(Plano plano, boolean inicio) {
        if (this.plano != null) {
            modoAnterior = this.plano.getModoOperacao();
        }

        this.plano = plano;
        this.tabelaDeTemposEntreVerde = this.plano.tabelaEntreVerde();
        this.listaOriginalEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        this.listaEstagioPlanos = new ArrayList<>(listaOriginalEstagioPlanos);
        contadorEstagio = 0;
        contadorIntervalo = 0L;
        contadorDeCiclos = 0L;


        if (inicio && listaEstagioPlanos.size() > 0) {
            this.estagioPlanoAtual = listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
        }


        geraIntervalos(0);

        if (!inicio) {
            EventoMotor eventoMotor = new EventoMotor(null, TipoEvento.TROCA_DE_PLANO_NO_ANEL, agendamento.getPlano().getPosicao(), agendamento.getAnel(), agendamento.getMomentoOriginal(), agendamento.getMomentoDaTroca());
            this.intervalos.get(0L).addEvento(contadorIntervalo, eventoMotor);
        }
    }

    private void geraIntervalos(Integer index) {
        GeradorDeIntervalos gerador = GeradorDeIntervalos.getInstance(this.intervalos, this.plano,
                this.modoAnterior, this.listaEstagioPlanos,
                this.estagioPlanoAtual, this.tabelaDeTemposEntreVerde,
                index);

        Pair<Integer, RangeMap<Long, IntervaloEstagio>> resultado = gerador.gerar(index);

        this.contadorEstagio += resultado.getFirst();
        this.intervalos = resultado.getSecond();

        modoAnterior = this.plano.getModoOperacao();
    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
        long offset = 0;
        if (this.intervalos.get(contadorIntervalo) == null) {
            offset = contadorIntervalo - 1;
        }
        this.intervalos.get(offset).addEvento(contadorIntervalo, eventoMotor);
        GerenciadorDeEventos.onEvento(this, eventoMotor);
    }

    public void atualizaEstagiosCicloAtual(EstagioPlano estagioPlano) {
        List<EstagioPlano> novaLista = new ArrayList<>();
        final Boolean[] adicionado = {false};
        listaEstagioPlanos.forEach(item -> {
            if (item.getPosicao() >= estagioPlano.getPosicao() && !adicionado[0]) {
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

    public Plano getPlano() {
        return plano;
    }

    public EstagioPlano getEstagioPlanoAtual() {
        return estagioPlanoAtual;
    }

    public List<EstagioPlano> getEstagiosProximoCiclo() {
        return estagiosProximoCiclo;
    }

    public List<EstagioPlano> getListaEstagioPlanos() {
        return listaEstagioPlanos;
    }

    public int getContadorEstagio() {
        return contadorEstagio;
    }

    public EstagioPlano getEstagioPlanoAnterior() {
        return estagioPlanoAnterior;
    }

    public long getContadorIntervalo() {
        return contadorIntervalo;
    }

    public RangeMap<Long, IntervaloEstagio> getIntervalos() {
        return intervalos;
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

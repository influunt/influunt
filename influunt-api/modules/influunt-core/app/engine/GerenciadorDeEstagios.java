package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import engine.eventos.GerenciadorDeEventos;
import engine.intervalos.GeradorDeIntervalos;
import models.*;
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

    private final GerenciadorDeEstagiosCallback callback;

    private final int anel;

    private final List<EstagioPlano> estagiosProximoCiclo = new ArrayList<>();

    private final Motor motor;

    private HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde;

    private List<EstagioPlano> listaOriginalEstagioPlanos;

    private Plano plano;

    private ModoOperacaoPlano modoAnterior;

    private RangeMap<Long, IntervaloEstagio> intervalos;

    private List<EstagioPlano> listaEstagioPlanos;

    private Long tempoDecorrido = 0L;

    private EstagioPlano estagioPlanoAtual;

    private EstagioPlano estagioPlanoAnterior;

    private long contadorIntervalo = 0L;

    private int contadorEstagio = 0;

    private long contadorDeCiclos = 0L;

    private GetIntervaloGrupoSemaforico intervaloGrupoSemaforicoAtual = null;

    private AgendamentoTrocaPlano agendamento = null;

    private Long tempoAbatimentoCoordenado = null;


    public GerenciadorDeEstagios(int anel,
                                 DateTime inicioControlador,
                                 DateTime inicioExecucao,
                                 Plano plano,
                                 GerenciadorDeEstagiosCallback callback,
                                 Motor motor) {

        this.anel = anel;
        this.inicioControlador = inicioControlador;
        this.inicioExecucao = inicioExecucao;
        this.callback = callback;
        this.motor = motor;

        reconhecePlano(plano, true);

    }

    public void tick() {
        IntervaloEstagio intervalo = this.intervalos.get(contadorIntervalo);

        if (this.agendamento != null && intervalo != null && (this.plano.isIntermitente() || this.plano.isApagada()) && !intervalo.isEntreverde()) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
            intervalo.setDuracao(contadorIntervalo - range.getKey().lowerEndpoint());
            executaAgendamentoTrocaDePlano();
            intervalo = this.intervalos.get(contadorIntervalo);
        } else if (this.agendamento != null && this.agendamento.isPlanoCoordenado() && !this.agendamento.isTempoDeEntradaCalculado()){
            tempoAbatimentoCoordenado = verificarETrocaCoordenado();
            this.agendamento.setTempoDeEntradaCalculado(true);
            intervalo = verificaETrocaIntervalo(intervalo);
        } else {
            intervalo = verificaETrocaIntervalo(intervalo);
        }

        verificaETrocaEstagio(intervalo);

        contadorIntervalo += 100L;
        tempoDecorrido += 100L;

        monitoraTempoMaximoDePermanenciaDoEstagio();
    }

    private Long verificarETrocaCoordenado() {
        return GerenciadorDeEstagiosHelper.reduzirTempoEstagio(estagioPlanoAnterior, intervalos, contadorIntervalo, estagioPlanoAtual);
    }

    private IntervaloEstagio verificaETrocaIntervalo(IntervaloEstagio intervalo) {
        if (intervalo == null) {
            contadorIntervalo = 0L;
            contadorEstagio++;
            if (this.agendamento != null && temQueExecutarOAgendamento()) {
                reiniciaContadorEstagio();
                contadorDeCiclos++;
                callback.onCicloEnds(this.anel, contadorDeCiclos);
                executaAgendamentoTrocaDePlano();
            } else if (contadorEstagio == listaEstagioPlanos.size()) {
                reiniciaContadorEstagio();
                verificaEAjustaIntermitenteCasoDemandaPrioritaria();

                if (this.agendamento != null && !this.plano.isManual()) {
                    geraIntervalos(0);
                    executaAgendamentoTrocaDePlano();
                } else {
                    atualizaListaEstagiosNovoCiclo(listaOriginalEstagioPlanos);
                    geraIntervalos(0);
                }

                contadorDeCiclos++;
                callback.onCicloEnds(this.anel, contadorDeCiclos);
            } else {
                geraIntervalos(contadorEstagio);
            }
            intervalo = this.intervalos.get(contadorIntervalo);
        }
        return intervalo;
    }

    private boolean temQueExecutarOAgendamento() {
        return this.agendamento.isImpostoPorFalha() ||
            this.agendamento.isSaidaDoModoManual() ||
            this.agendamento.isPlanoCoordenado();
    }

    private void verificaEAjustaIntermitenteCasoDemandaPrioritaria() {
        if (!this.plano.isModoOperacaoVerde() &&
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

    private boolean monitoraTempoMaximoDePermanenciaDoEstagio() {
        Estagio estagio = estagioPlanoAtual.getEstagio();
        if (estagio.isTempoMaximoPermanenciaAtivado()) {
            long tempoMaximoEstagio = estagio.getTempoMaximoPermanencia() * 1000L;
            if (intervaloGrupoSemaforicoAtual != null && intervaloGrupoSemaforicoAtual.getIntervaloEntreverde() != null) {
                tempoMaximoEstagio += intervaloGrupoSemaforicoAtual.getIntervaloEntreverde().getDuracao();
            }
            if (contadorIntervalo >= tempoMaximoEstagio) {
                if (plano.isManual()) {
                    motor.onEvento(new EventoMotor(inicioExecucao.plus(tempoDecorrido), TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL));
                } else {
                    motor.onEvento(new EventoMotor(inicioExecucao.plus(tempoDecorrido), TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO, getAnel()));
                }
                return true;
            }
        }
        return false;
    }

    private boolean planoComEstagioUnico() {
        return this.plano.isModoOperacaoVerde() && this.listaEstagioPlanos.size() == 1 && contadorIntervalo == 0L;
    }

    private void executaAgendamentoTrocaDePlano() {
        if (this.plano.isManual() && !this.agendamento.isSaidaDoModoManual()) {
            this.agendamento = null;
            trocarPlano(new AgendamentoTrocaPlano(null, getEstagioPlanoAnterior().getPlano(), null));
        } else {
            agendamento.setMomentoDaTroca(tempoDecorrido);
            callback.onTrocaDePlanoEfetiva(agendamento);
            reconhecePlano(this.agendamento.getPlano());
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

    private void reconhecePlano(Plano plano, boolean inicio) {
        if (this.plano == null || !this.plano.isImpostoPorFalha()) {
            if (this.plano != null) {
                modoAnterior = this.plano.getModoOperacao();
            }

            this.plano = plano;
            this.tabelaDeTemposEntreVerde = this.plano.tabelaEntreVerde();
            this.listaOriginalEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();

            if (this.plano.isTempoFixoIsolado() || this.plano.isAtuado()) {
                atualizaListaEstagiosNovoPlano(listaOriginalEstagioPlanos);
            } else if (this.plano.isTempoFixoCoordenado()) {
                final Evento evento = motor.getEventoAtual();
                this.listaEstagioPlanos = listaEstagioPlanosSincronizada(this.plano.getEstagiosOrdenados(), evento.getMomentoEntrada(getAnel(), this.agendamento.getMomentoOriginal()));
            } else {
                this.listaEstagioPlanos = new ArrayList<>(listaOriginalEstagioPlanos);
            }

            reiniciaContadorEstagio();
            contadorIntervalo = 0L;
            contadorDeCiclos = 0L;

            if (inicio && listaEstagioPlanos.size() > 0) {
                this.estagioPlanoAtual = listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
            }

            geraIntervalos(0);

            if (!inicio) {
                IntervaloEstagio intervalo = this.intervalos.get(0L);
                EventoMotor eventoMotor = new EventoMotor(null, TipoEvento.TROCA_DE_PLANO_NO_ANEL, agendamento.getPlano().getPosicao(), agendamento.getAnel(), agendamento.getMomentoOriginal(), agendamento.getMomentoDaTroca());

                if (intervalo == null) {
                    this.agendamento = null;
                    verificaETrocaIntervalo(intervalo);
                }

                this.intervalos.get(0L).addEvento(contadorIntervalo, eventoMotor);
            }
        }
    }

    private List<EstagioPlano> listaEstagioPlanosSincronizada(List<EstagioPlano> estagiosOrdenados, Long momentoEntrada) {
        List<EstagioPlano> novaLista = new ArrayList<>();
        final long[] tempoRestante = {momentoEntrada};
        estagiosOrdenados.stream().forEach(estagioPlano -> {
            final long duracaoEstagio = estagioPlano.getDuracaoEstagio() * 1000L;
            if (tempoRestante[0] >= duracaoEstagio) {
                tempoRestante[0] -= duracaoEstagio;
            } else {
                novaLista.add(estagioPlano);
            }
        });
        if (tempoAbatimentoCoordenado != null) {
            tempoAbatimentoCoordenado += tempoRestante[0];
        } else {
            tempoAbatimentoCoordenado = tempoRestante[0];
        }
        return novaLista;
    }

    private void geraIntervalos(Integer index) {
        GeradorDeIntervalos gerador = GeradorDeIntervalos.getInstance(this.intervalos, this.plano,
            this.modoAnterior, this.listaEstagioPlanos,
            this.estagioPlanoAtual, this.tabelaDeTemposEntreVerde,
            index, tempoAbatimentoCoordenado);

        Pair<Integer, RangeMap<Long, IntervaloEstagio>> resultado = gerador.gerar(index);

        this.tempoAbatimentoCoordenado = gerador.getTempoAbatimentoCoordenado();

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

        this.plano.getEstagiosPlanos().stream()
            .filter(EstagioPlano::isDispensavel)
            .filter(estagioPlano -> estagioPlano.getEstagio().getDetector().isComFalha())
            .forEach(estagioPlano -> estagiosProximoCiclo.add(estagioPlano));

        estagiosProximoCiclo.forEach(estagioPlano -> {
            atualizaEstagiosCicloAtual(estagioPlano);
        });

        estagiosProximoCiclo.clear();
    }

    private void atualizaListaEstagiosNovoPlano(List<EstagioPlano> lista) {
        this.listaEstagioPlanos = new ArrayList<>(lista);
        estagiosProximoCiclo.forEach(estagioPlano -> {
            EstagioPlano novoEstagioPlano = this.plano.getEstagiosPlanos()
                .stream()
                .filter(estagioPlano1 -> {
                    return estagioPlano1.isDispensavel() && estagioPlano.getEstagio().equals(estagioPlano1.getEstagio());
                }).findFirst().orElse(null);

            if (novoEstagioPlano != null) {
                atualizaEstagiosCicloAtual(novoEstagioPlano);
            }
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

    public Motor getMotor() {
        return motor;
    }

    public void reiniciaContadorEstagio() {
        contadorEstagio = 0;
    }

    public IntervaloGrupoSemaforico getIntervalosGruposSemaforicos() {
        return new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde());
    }

    public Detector getDetector(Integer posicao, TipoDetector tipoDetector) {
        return getPlano().getAnel().getDetectores().stream().filter(detector -> {
            return posicao.equals(detector.getPosicao()) && tipoDetector.equals(detector.getTipo());
        }).findFirst().orElse(null);
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

package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import engine.eventos.GerenciadorDeEventos;
import engine.intervalos.GeradorDeIntervalos;
import helpers.GerenciadorEstagiosHelper;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by rodrigosol on 10/11/16.
 */
public class GerenciadorDeEstagios implements EventoCallback {

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

    private long contadorTempoEstagio = 0L;

    private long contadorTempoCiclo = 0L;

    private int contadorEstagio = 0;

    private int contadorDeCiclos = 0;

    private GetIntervaloGrupoSemaforico intervaloGrupoSemaforicoAtual = null;

    private AgendamentoTrocaPlano agendamento = null;

    private Long tempoAbatimentoCoordenado = null;

    private Long tempoAbatidoNoCiclo = 0L;

    private RangeMap<Long, EventoMotor> eventosAgendados = TreeRangeMap.create();

    public boolean isTempoDispensavelJaAdicionado() {
        return tempoDispensavelJaAdicionado;
    }

    private boolean tempoDispensavelJaAdicionado = false;

    public GerenciadorDeEstagios(int anel,
                                 DateTime inicioControlador,
                                 Plano plano,
                                 GerenciadorDeEstagiosCallback callback,
                                 Motor motor) {

        this.anel = anel;
        this.inicioExecucao = inicioControlador;
        this.callback = callback;
        this.motor = motor;

        reconhecePlano(plano, true);

    }

    public void tick() {
        IntervaloEstagio intervalo = this.intervalos.get(contadorIntervalo);

        if (this.agendamento != null && intervalo != null &&
            (this.plano.isIntermitente() || this.plano.isApagada()) &&
            !intervalo.isEntreverde()) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
            intervalo.setDuracao(contadorIntervalo - range.getKey().lowerEndpoint());
            executaAgendamentoTrocaDePlano();
            intervalo = this.intervalos.get(contadorIntervalo);
        } else if (this.agendamento != null && this.agendamento.isPlanoCoordenado() &&
            !this.agendamento.isTempoDeEntradaCalculado()) {
            tempoAbatimentoCoordenado = verificarETrocaCoordenado();
            this.agendamento.setTempoDeEntradaCalculado(true);
            intervalo = verificaETrocaIntervalo(intervalo);
        } else {
            intervalo = verificaETrocaIntervalo(intervalo);
        }

        verificaETrocaEstagio(intervalo);

        contadorIntervalo += 100L;
        contadorTempoEstagio += 100L;
        contadorTempoCiclo += 100L;
        tempoDecorrido += 100L;

        if (monitoraTempoMaximoDePermanenciaDoEstagio()) {
            contadorTempoEstagio = 0L;
        }

        if (eventosAgendados.get(inicioExecucao.plus(tempoDecorrido).getMillis()) != null) {
            executaEventoAgendamento();
        }
    }

    private void executaEventoAgendamento() {
        Map.Entry<Range<Long>, EventoMotor> range = eventosAgendados.getEntry(inicioExecucao.plus(tempoDecorrido).getMillis());
        EventoMotor evento = range.getValue();
        if (TipoEvento.IMPOSICAO_PLANO.equals(evento.getTipoEvento()) ||
            TipoEvento.IMPOSICAO_MODO.equals(evento.getTipoEvento())) {
            onEvento(evento);
        } else {
            motor.onEvento(evento);
        }
        eventosAgendados.remove(range.getKey());
    }

    private Long verificarETrocaCoordenado() {
        return GerenciadorDeEstagiosHelper.reduzirTempoEstagio(estagioPlanoAnterior, intervalos, contadorIntervalo, estagioPlanoAtual);
    }

    private IntervaloEstagio verificaETrocaIntervalo(IntervaloEstagio intervalo) {
        if (intervalo == null) {
            if (verificaTempoVerdeSeguranca() || verificaTempoEstagioDispensavel()) {
                intervalo = this.intervalos.get(contadorIntervalo);
                return intervalo;
            }

            tempoDispensavelJaAdicionado = false;
            contadorIntervalo = 0L;
            contadorEstagio++;
            if (this.agendamento != null && temQueExecutarOAgendamento()) {
                reiniciaContadorEstagio();
                fecharCiclo();
                executaAgendamentoTrocaDePlano();
            } else if (this.plano.isModoOperacaoVerde() && estagioPlanoAtual.getEstagio().isDemandaPrioritaria()) {
                reconhecePlano(this.plano);
            } else if (contadorEstagio == listaEstagioPlanos.size()) {
                reiniciaContadorEstagio();
                fecharCiclo();

                verificaEAjustaIntermitenteCasoDemandaPrioritaria();

                if (this.agendamento != null && !this.plano.isManual()) {
                    geraIntervalos(0);
                    executaAgendamentoTrocaDePlano();
                } else {
                    atualizaListaEstagiosNovoCiclo(listaOriginalEstagioPlanos);
                    geraIntervalos(0);
                }


            } else {
                geraIntervalos(contadorEstagio);
            }
            intervalo = this.intervalos.get(contadorIntervalo);
        }
        return intervalo;
    }

    private void fecharCiclo() {
        contadorDeCiclos++;
        contadorTempoCiclo = 0L;
        tempoAbatidoNoCiclo = 0L;
        callback.onCicloEnds(this.anel, contadorDeCiclos);
    }

    private boolean verificaTempoVerdeSeguranca() {
        IntervaloEstagio intervalo = this.intervalos.get(this.contadorIntervalo - 100L);
        if (this.agendamento != null && intervalo != null &&
            !this.agendamento.isImpostoPorFalha() &&
            intervalo.getDuracao() < (estagioPlanoAtual.getTempoVerdeSeguranca() * 1000L)) {
            Plano plano = this.agendamento.getPlano();
            List<EstagioPlano> estagios = plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();
            List<EstagioPlano> lista = new ArrayList<>();
            lista.add(estagioPlanoAnterior);
            lista.add(estagioPlanoAtual);
            if (!estagios.isEmpty()) {
                lista.add(estagios.get(0));
            }
            if (!plano.isTempoFixoCoordenado() && !GerenciadorDeEstagiosHelper.isCumpreTempoVerdeSeguranca(lista)) {
                GerenciadorDeEstagiosHelper.aumentarTempoEstagio(this.intervalos,
                    this.contadorIntervalo,
                    estagioPlanoAtual.getTempoVerdeSeguranca() * 1000L);
                return true;
            }
        }
        return false;
    }

    private boolean verificaTempoEstagioDispensavel() {
        EstagioPlano proximoEstagio = estagioPlanoAtual.getEstagioPlanoProximo(plano.getEstagiosOrdenados());
        if (plano.isTempoFixoCoordenado() &&
            proximoEstagio != null &&
            proximoEstagio.isDispensavel() &&
            !tempoDispensavelJaAdicionado &&
            estagioPlanoAtual.equals(proximoEstagio.getEstagioQueRecebeEstagioDispensavel()) &&
            !listaEstagioPlanos.contains(proximoEstagio)) {

            int tempoAdicional = proximoEstagio.getTempoVerde();

            EstagioPlano proximoDoProximo = proximoEstagio.getEstagioPlanoProximo(plano.getEstagiosOrdenados());
            EstagioPlano anteriorEstagio = estagioPlanoAtual.getEstagioPlanoAnterior(plano.getEstagiosOrdenados());

            Long entreverdeOriginal = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioPlanoAtual.getEstagio().getPosicao(),
                proximoEstagio.getEstagio().getPosicao()));

            //Verifica a diferenca de entreverde do proximo estágio, caso não exista mais estágios dispensaveis
            if (proximoEstagio.ultimoEstagioDispensavel() && !proximoEstagio.ultimoEstagioDaSequencia()) {
                entreverdeOriginal += tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(proximoEstagio.getEstagio().getPosicao(),
                    proximoDoProximo.getEstagio().getPosicao()));

                entreverdeOriginal -= tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioPlanoAtual.getEstagio().getPosicao(),
                    proximoDoProximo.getEstagio().getPosicao()));
            }


            //Verifica diferenca entreverde do inicio do estagio atual
            if (!anteriorEstagio.equals(estagioPlanoAnterior)) {
                entreverdeOriginal += tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(anteriorEstagio.getEstagio().getPosicao(),
                    estagioPlanoAtual.getEstagio().getPosicao()));

                entreverdeOriginal -= tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioPlanoAnterior.getEstagio().getPosicao(),
                    estagioPlanoAtual.getEstagio().getPosicao()));
            }


            tempoAdicional += (entreverdeOriginal / 1000);
            long novoTempo = tempoAdicional * 1000L;

            if (tempoAbatimentoCoordenado > 0L) {

                if (tempoAbatimentoCoordenado <= novoTempo) {
                    novoTempo = novoTempo - tempoAbatimentoCoordenado;
                    tempoAbatidoNoCiclo += tempoAbatimentoCoordenado;
                    tempoAbatimentoCoordenado = 0L;
                } else {
                    tempoAbatimentoCoordenado = tempoAbatimentoCoordenado - novoTempo;
                    tempoAbatidoNoCiclo += novoTempo;
                    novoTempo = 0L;
                }

            }

            IntervaloEstagio intervalo = this.intervalos.get(this.contadorIntervalo - 100L);

            tempoDispensavelJaAdicionado = true;
            if (intervalo != null && novoTempo > 0L) {

                GerenciadorDeEstagiosHelper.aumentarTempoEstagio(this.intervalos,
                    this.contadorIntervalo,
                    novoTempo + intervalo.getDuracao());

                return true;
            }
            return false;
        }

        return false;
    }

    private boolean temQueExecutarOAgendamento() {
        return this.agendamento.isImpostoPorFalha() ||
            this.agendamento.isSaidaDoModoManual() ||
            this.agendamento.isPlanoCoordenado() ||
            this.agendamento.isImposicaoPlano() ||
            this.agendamento.isSaidaImposicao() ||
            (this.agendamento.getPlano().isManual() && motor.isEntrarEmModoManualAbrupt());
    }

    private boolean naoPodeExecutarOAgendamento() {
        return (this.plano.isManual() && !this.agendamento.isSaidaDoModoManual()) ||
            this.plano.isImposto();
    }

    private boolean podeAgendar(AgendamentoTrocaPlano agendamento) {
        return ((this.plano.isManual() && agendamento.isSaidaDoModoManual()) ||
            (this.plano.isImposto() && agendamento.isSaidaImposicao()) ||
            (!this.plano.isManual() && !this.plano.isImposto()));
    }

    private void verificaEAjustaIntermitenteCasoDemandaPrioritaria() {
        if (!this.plano.isModoOperacaoVerde() &&
            estagioPlanoAtual.getEstagio().isDemandaPrioritaria()) {
            this.modoAnterior = ModoOperacaoPlano.TEMPO_FIXO_ISOLADO;
        }
    }

    private void verificaETrocaEstagio(IntervaloEstagio intervalo) {
        EstagioPlano estagioPlano = intervalo.getEstagioPlano();
        if (!estagioPlano.equals(estagioPlanoAtual) || planoComEstagioUnico() || trocaPlanoAbrupt(estagioPlano, estagioPlanoAtual)) {
            if (intervaloGrupoSemaforicoAtual != null) {
                IntervaloGrupoSemaforico intervaloGrupoSemaforico = new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde());
                callback.onEstagioEnds(this.anel, contadorDeCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido), intervaloGrupoSemaforico);

                estagioPlanoAnterior = estagioPlanoAtual;

                if (!estagioPlanoAnterior.getEstagio().equals(estagioPlano.getEstagio())) {
                    contadorTempoEstagio = 0L;
                }
            }

            intervaloGrupoSemaforicoAtual = new GetIntervaloGrupoSemaforico().invoke();
            IntervaloGrupoSemaforico intervalos = new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde());

            callback.onEstagioChange(this.anel, contadorDeCiclos, tempoDecorrido, inicioExecucao.plus(tempoDecorrido),
                intervalos);

            estagioPlanoAtual = estagioPlano;
        }

    }

    private boolean trocaPlanoAbrupt(EstagioPlano estagioPlano, EstagioPlano estagioPlanoAtual) {
        Plano plano = estagioPlanoAtual.getPlano();
        Plano novoPlano = estagioPlano.getPlano();
        if (novoPlano.equals(plano) && novoPlano.getModoOperacao().equals(plano.getModoOperacao())) {
            return false;
        }
        return true;
    }

    private boolean monitoraTempoMaximoDePermanenciaDoEstagio() {
        Estagio estagio = estagioPlanoAtual.getEstagio();
        if (estagio.isTempoMaximoPermanenciaAtivado()) {
            long tempoMaximoEstagio = estagio.getTempoMaximoPermanencia() * 1000L;
            if (intervaloGrupoSemaforicoAtual != null && intervaloGrupoSemaforicoAtual.getIntervaloEntreverde() != null) {
                tempoMaximoEstagio += intervaloGrupoSemaforicoAtual.getIntervaloEntreverde().getDuracao();
            }
            if (contadorTempoEstagio >= tempoMaximoEstagio && this.intervalos.get(contadorIntervalo + 100L) != null) {
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
        if (!naoPodeExecutarOAgendamento()) {
            agendamento.setMomentoDaTroca(tempoDecorrido);
            callback.onTrocaDePlanoEfetiva(agendamento);
            reconhecePlano(this.agendamento.getPlano());
            this.agendamento = null;
        }

    }

    public void trocarPlano(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        if (podeAgendar(agendamentoTrocaPlano)) {
            agendamentoTrocaPlano.setMomentoPedidoTroca(tempoDecorrido);
            agendamentoTrocaPlano.setAnel(anel);
            agendamento = agendamentoTrocaPlano;
        }
    }

    private void reconhecePlano(Plano plano) {
        reconhecePlano(plano, false);
    }

    private void reconhecePlano(Plano plano, boolean inicio) {
        if (this.plano == null || !this.plano.isImpostoPorFalha()) {
            if (this.plano != null) {
                modoAnterior = this.plano.getModoOperacao();

                if (this.plano.isManual() && !plano.isManual()) {
                    motor.desativaModoManual();
                }
            }

            this.plano = plano;

            if (plano.isManual()) {
                motor.ativaModoManual();
            }

            this.tabelaDeTemposEntreVerde = this.plano.tabelaEntreVerde();
            this.listaOriginalEstagioPlanos = this.plano.ordenarEstagiosPorPosicaoSemEstagioDispensavel();

            if (this.plano.isTempoFixoIsolado() || this.plano.isAtuado()) {
                atualizaListaEstagiosNovoPlano(listaOriginalEstagioPlanos);
            } else if (this.plano.isTempoFixoCoordenado()) {
                final Evento evento = motor.getEventoAtual();
                DateTime momentoEntrada = this.agendamento != null ? this.agendamento.getMomentoOriginal() : inicioExecucao.plus(tempoDecorrido);
                this.listaEstagioPlanos = listaEstagioPlanosSincronizada(this.plano.getEstagiosOrdenados(), evento.getMomentoEntrada(getAnel(), momentoEntrada));
            } else {
                this.listaEstagioPlanos = new ArrayList<>(listaOriginalEstagioPlanos);
            }

            reiniciaContadorEstagio();
            contadorIntervalo = 0L;
            contadorDeCiclos = 0;
            contadorTempoCiclo = 0L;

            if (inicio && listaEstagioPlanos.size() > 0) {
                this.estagioPlanoAtual = listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
            }

            geraIntervalos(0, inicio);

            if (!inicio && this.agendamento != null) {
                IntervaloEstagio intervalo = this.intervalos.get(0L);
                EventoMotor eventoMotor = new EventoMotor(getTimestamp(), TipoEvento.TROCA_DE_PLANO_NO_ANEL, agendamento.getPlano().getPosicao(), agendamento.getAnel(), agendamento.getMomentoOriginal(), agendamento.getMomentoDaTroca());
                if (intervalo == null) {
                    this.agendamento = null;
                    contadorTempoEstagio = 0L;
                    verificaETrocaIntervalo(intervalo);
                }

                this.intervalos.get(0L).addEvento(contadorIntervalo, eventoMotor);
            }
        }
    }

    private List<EstagioPlano> listaEstagioPlanosSincronizada(List<EstagioPlano> estagiosOrdenados, Long momentoEntrada) {
        List<EstagioPlano> novaLista = new ArrayList<>();
        final long[] tempoRestante = {momentoEntrada};

        final EstagioPlano estagioPlanoMomentoEntrada = this.plano.getEstagioPlanoNoMomento(momentoEntrada);

        if (estagioPlanoAtual != null &&
            estagioPlanoAtual.getEstagio() != null &&
            estagioPlanoAtual.getEstagio().temTransicaoProibidaParaEstagio(estagioPlanoMomentoEntrada.getEstagio())) {

            final EstagioPlano estagioPlanoAlternativo = GerenciadorEstagiosHelper.getEstagioPlanoAlternativoDaTransicaoProibida(estagioPlanoAtual.getEstagio(),
                estagioPlanoMomentoEntrada.getEstagio(), estagiosOrdenados);

            estagiosOrdenados.stream().forEach(estagioPlano -> {
                if (estagioPlano.getPosicao() < estagioPlanoAlternativo.getPosicao()) {
                    tempoRestante[0] -= estagioPlano.getDuracaoEstagio() * 1000L;
                } else {
                    novaLista.add(estagioPlano);
                }
            });

        } else {
            estagiosOrdenados.stream().forEach(estagioPlano -> {
                final long duracaoEstagio = estagioPlano.getDuracaoEstagio() * 1000L;
                //Faz abatimento até enquanto a lista estiver vazia
                if (tempoRestante[0] >= duracaoEstagio && novaLista.isEmpty()) {
                    tempoRestante[0] -= duracaoEstagio;
                } else {
                    novaLista.add(estagioPlano);
                }
            });
        }

        if (tempoAbatimentoCoordenado != null) {
            tempoAbatimentoCoordenado += tempoRestante[0];
        } else {
            tempoAbatimentoCoordenado = tempoRestante[0];
        }

        return novaLista;
    }

    private void geraIntervalos(Integer index) {
        geraIntervalos(index, false);
    }

    private void geraIntervalos(Integer index, boolean inicio) {
        GeradorDeIntervalos gerador = GeradorDeIntervalos.getInstance(this.intervalos, this.plano,
            this.modoAnterior, this.listaEstagioPlanos,
            this.estagioPlanoAtual, this.tabelaDeTemposEntreVerde,
            index, tempoAbatimentoCoordenado, inicio, contadorTempoEstagio,
            contadorTempoCiclo, contadorDeCiclos, tempoAbatidoNoCiclo);

        Pair<Integer, RangeMap<Long, IntervaloEstagio>> resultado = gerador.gerar(index);

        this.tempoAbatimentoCoordenado = gerador.getTempoAbatimentoCoordenado();
        this.tempoAbatidoNoCiclo = gerador.getTempoAbatidoNoCiclo();

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
            if (item.getPosicao() != null && item.getPosicao() >= estagioPlano.getPosicao() && !adicionado[0]) {
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

    public Plano getPlano(Integer posicao) {
        return getPlano().getAnel().findPlanoByPosicao(posicao);
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

    public int getContadorTempoCicloEmSegundos() {
        if (plano.isModoOperacaoVerde() && !plano.isManual()) {
            return (int) (contadorTempoCiclo / 1000L);
        }
        return 0;
    }

    public String getEstagioAtual() {
        return getEstagioPlanoAtual().getEstagio().getId() != null ?
            getEstagioPlanoAtual().getEstagio().toString() : "";
    }

    public String getPosicaoPlano() {
        if (plano.isManual() || plano.isImpostoPorFalha()) {
            return "";
        }
        return plano.getPosicao().toString();
    }

    public IntervaloGrupoSemaforico getIntervalosGruposSemaforicos() {
        return new IntervaloGrupoSemaforico(intervaloGrupoSemaforicoAtual.getIntervaloEntreverde(), intervaloGrupoSemaforicoAtual.getIntervaloVerde());
    }

    public Detector getDetector(Integer posicao, TipoDetector tipoDetector) {
        return getPlano().getAnel().getDetectores().stream().filter(detector -> {
            return posicao.equals(detector.getPosicao()) && tipoDetector.equals(detector.getTipo());
        }).findFirst().orElse(null);
    }

    public void agendarEvento(DateTime inicio, EventoMotor eventoMotor) {
        eventosAgendados.put(Range.atLeast(inicio.getMillis()), eventoMotor);
    }

    public void agendarEvento(DateTime inicio, DateTime fim, EventoMotor eventoMotor) {
        eventosAgendados.put(Range.closedOpen(inicio.getMillis(), fim.getMillis()), eventoMotor);
    }

    public void limparAgendamentos(TipoEvento liberarImposicao) {
        Iterator<Map.Entry<Range<Long>, EventoMotor>> it = eventosAgendados.asMapOfRanges().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Range<Long>, EventoMotor> entry = it.next();
            if (TipoEvento.LIBERAR_IMPOSICAO.equals(entry.getValue().getTipoEvento())) {
                it.remove();
            }
        }
    }

    public RangeMap<Long, EventoMotor> getEventosAgendados() {
        return eventosAgendados;
    }

    public int getTempoRestanteDoEstagio() {
        if (plano.isModoOperacaoVerde() && !plano.isManual()) {
            Long tempoEstagio = this.intervalos.getEntry(0L).getKey().upperEndpoint();
            final Map.Entry<Range<Long>, IntervaloEstagio> verde = this.intervalos.getEntry(tempoEstagio + 1);
            if (verde != null) {
                tempoEstagio = verde.getKey().upperEndpoint();
            }
            return (int) ((tempoEstagio - contadorIntervalo) / 1000L);
        }
        return 0;
    }

    public int getTempoRestanteDoCiclo() {
        if (plano.getTempoCiclo() != null && plano.isModoOperacaoVerde() && !plano.isManual()) {
            return (int) (((plano.getTempoCiclo() * 1000L) - contadorTempoCiclo) / 1000L);
        } else {
            return 0;
        }

    }

    public DateTime getTimestamp() {
        return inicioExecucao.plus(tempoDecorrido);
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
            if (intervaloVerde == null && !intervaloEntreverde.isEntreverde()) {
                intervaloVerde = intervaloEntreverde;
                intervaloEntreverde = null;
            }
            return this;
        }
    }
}

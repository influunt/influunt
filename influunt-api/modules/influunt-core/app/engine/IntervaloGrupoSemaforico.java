package engine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import helpers.GerenciadorEstagiosHelper;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import play.libs.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static helpers.GerenciadorEstagiosHelper.TEMPO_INTERMITENTE;
import static helpers.GerenciadorEstagiosHelper.TEMPO_VERMELHO_INTEGRAL;
import static models.EstadoGrupoSemaforico.*;

public class IntervaloGrupoSemaforico {
    private final IntervaloEstagio entreverde;

    private final IntervaloEstagio verde;

    private final long duracao;

    private final long duracaoEntreverde;

    private final long duracaoVerde;

    private final EstagioPlano estagioPlano;

    private final EstagioPlano estagioPlanoAnterior;

    private final Estagio estagio;

    private final Estagio estagioAnterior;

    private final Plano plano;

    private final long duracaoEntreverdeSemAtraso;

    private HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> estados;

    public IntervaloGrupoSemaforico(IntervaloEstagio entreverde, IntervaloEstagio verde) {
        this(entreverde, verde, false);
    }

    public IntervaloGrupoSemaforico(IntervaloEstagio entreverde, IntervaloEstagio verde,
                                    boolean acrescimoVerde) {
        IntervaloEstagio intervalo = null;

        if (entreverde != null) {
            this.duracaoEntreverde = entreverde.getDuracao();
            intervalo = entreverde;

            EstagioPlano origem = intervalo.getEstagioPlanoAnterior();
            EstagioPlano destino = intervalo.getEstagioPlano();

            if (energizacaoGrupo(origem, destino) && !intervalo.isInicio()) {
                this.duracaoEntreverdeSemAtraso = intervalo.getEstagioPlanoAnterior().getPlano()
                    .tabelaEntreVerde()
                    .get(new Pair<Integer, Integer>(origem.getEstagio().getPosicao(),
                        destino.getEstagio().getPosicao()));
            } else {
                this.duracaoEntreverdeSemAtraso = Math.max(this.duracaoEntreverde - entreverde.getDiffEntreVerde(), 0L);
            }
        } else {
            this.duracaoEntreverde = 0L;
            this.duracaoEntreverdeSemAtraso = 0L;
        }

        if (verde != null) {
            this.duracaoVerde = verde.getDuracao();
            intervalo = verde;
        } else {
            this.duracaoVerde = 0L;
        }

        this.duracao = this.duracaoEntreverde + this.duracaoVerde;
        this.entreverde = entreverde;
        this.verde = verde;

        this.estagioPlano = intervalo.getEstagioPlano();
        this.estagioPlanoAnterior = intervalo.getEstagioPlanoAnterior();

        this.estagio = this.estagioPlano.getEstagio();

        if (this.estagioPlanoAnterior != null) {
            this.estagioAnterior = this.estagioPlanoAnterior.getEstagio();
        } else {
            this.estagioAnterior = null;
        }
        this.plano = estagioPlano.getPlano();

        if (acrescimoVerde) {
            loadAcrescimoVerde();
        } else {
            if (plano.isIntermitente() || plano.isApagada()) {
                loadEstadosFixos();
            } else if (intervalo.isInicio()) {
                loadEstadosComSequenciaDePartida();
            } else {
                loadEstados();
            }
        }
    }

    private void loadEstadosComSequenciaDePartida() {
        estados = new HashMap<>();
        loadEstagioSequenciaDePartida(verde.getDuracao());
    }

    private void loadEstados() {
        estados = new HashMap<>();

        if (estagioPlanoAnterior != null &&
            !estagioPlanoAnterior.getPlano().isModoOperacaoVerde()) {

            if (estagioPlanoAnterior.getPlano().isIntermitente()) {
                loadEstagioPosModoIntermitente(verde.getDuracao());
            } else {
                loadEstagioSequenciaDePartida(verde.getDuracao());
            }


        } else {
            estagio.getGruposSemaforicos().stream().forEach(grupoSemaforico -> {
                GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGrupoSemaforicoPlano(grupoSemaforico);
                if (grupoSemaforicoPlano.isAtivado()) {
                    estados.put(grupoSemaforico.getPosicao(), loadGrupoSemaforico(grupoSemaforico));
                } else {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), loadGrupoSemaforico(grupoSemaforicoPlano));
                }
            });

            plano.getGruposSemaforicosPlanos().stream()
                .filter(grupoSemaforicoPlano -> !estagio.getGruposSemaforicos().contains(grupoSemaforicoPlano.getGrupoSemaforico()))
                .forEach(grupoSemaforicoPlano -> {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), loadGrupoSemaforico(grupoSemaforicoPlano));
                });
        }

    }

    private void loadAcrescimoVerde() {
        estados = new HashMap<>();

        RangeMap<Long, EstadoGrupoSemaforico> intervaloVerde = TreeRangeMap.create();
        intervaloVerde.put(Range.closedOpen(0L, verde.getDuracao()), VERDE);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloVermelho = TreeRangeMap.create();
        intervaloVermelho.put(Range.closedOpen(0L, verde.getDuracao()), VERMELHO);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloDesligado = TreeRangeMap.create();
        intervaloDesligado.put(Range.closedOpen(0L, verde.getDuracao()), DESLIGADO);

        plano.getGruposSemaforicosPlanos().stream()
            .filter(grupoSemaforicoPlano -> estagio.getGruposSemaforicos().contains(grupoSemaforicoPlano.getGrupoSemaforico()))
            .forEach(grupoSemaforicoPlano -> {
                if (grupoSemaforicoPlano.isAtivado()) {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), intervaloVerde);
                } else {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), intervaloDesligado);
                }
            });

        plano.getGruposSemaforicosPlanos().stream()
            .filter(grupoSemaforicoPlano -> !estagio.getGruposSemaforicos().contains(grupoSemaforicoPlano.getGrupoSemaforico()))
            .forEach(grupoSemaforicoPlano -> {
                if (grupoSemaforicoPlano.isAtivado()) {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), intervaloVermelho);
                } else {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), intervaloDesligado);
                }
            });

    }

    private void loadEstagioSequenciaDePartida(Long tempoVerdeEstagio) {
        final long tempoTotalSequenciaPartida = GerenciadorEstagiosHelper.TEMPO_SEQUENCIA_DE_PARTIDA;
        final long tempoVerde = tempoTotalSequenciaPartida + tempoVerdeEstagio;

        plano.getGruposSemaforicosPlanos().stream()
            .forEach(grupoSemaforicoPlano -> {
                final GrupoSemaforico grupo = grupoSemaforicoPlano.getGrupoSemaforico();
                RangeMap<Long, EstadoGrupoSemaforico> intervalo = TreeRangeMap.create();

                if (grupoSemaforicoPlano.isAtivado()) {
                    intervalo.put(Range.closedOpen(0L, 5000L),
                        grupo.isVeicular() ? AMARELO_INTERMITENTE : DESLIGADO);

                    intervalo.put(Range.closedOpen(5000L, tempoTotalSequenciaPartida), VERMELHO);


                    intervalo.put(Range.closedOpen(tempoTotalSequenciaPartida, tempoVerde),
                        estagio.getGruposSemaforicos().contains(grupo) ? VERDE : VERMELHO);
                } else {
                    intervalo.put(Range.closedOpen(0L, 5000L), DESLIGADO);
                    intervalo.put(Range.closedOpen(5000L, tempoTotalSequenciaPartida), DESLIGADO);
                    intervalo.put(Range.closedOpen(tempoTotalSequenciaPartida, tempoVerde), DESLIGADO);
                }

                estados.put(grupo.getPosicao(), intervalo);
            });
    }

    private void loadEstagioPosModoIntermitente(Long tempoVerdeEstagio) {
        RangeMap<Long, EstadoGrupoSemaforico> intervaloVermelho = TreeRangeMap.create();
        intervaloVermelho.put(Range.closedOpen(0L, TEMPO_VERMELHO_INTEGRAL), VERMELHO);
        intervaloVermelho.put(Range.closedOpen(TEMPO_VERMELHO_INTEGRAL, TEMPO_VERMELHO_INTEGRAL + tempoVerdeEstagio),
            VERMELHO);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloVerde = TreeRangeMap.create();
        intervaloVerde.put(Range.closedOpen(0L, TEMPO_VERMELHO_INTEGRAL), VERMELHO);
        intervaloVerde.put(Range.closedOpen(TEMPO_VERMELHO_INTEGRAL, TEMPO_VERMELHO_INTEGRAL + tempoVerdeEstagio),
            VERDE);

        plano.getGruposSemaforicosPlanos().stream()
            .forEach(grupoSemaforicoPlano -> {
                final GrupoSemaforico grupo = grupoSemaforicoPlano.getGrupoSemaforico();
                if (estagio.getGruposSemaforicos().contains(grupo)) {
                    estados.put(grupo.getPosicao(), intervaloVerde);
                } else {
                    estados.put(grupo.getPosicao(), intervaloVermelho);
                }
            });
    }

    private void loadEstadosFixos() {
        estados = new HashMap<>();
        if (estagio.isDemandaPrioritaria()) {

            loadEstadosEntradaDemandaPrioritaria();

        } else if (estagioPlanoAnterior != null &&
            estagioPlanoAnterior.getPlano().isModoOperacaoVerde()) {

            plano.getGruposSemaforicosPlanos().stream()
                .forEach(grupoSemaforicoPlano -> {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(),
                        loadGrupoSemaforico(grupoSemaforicoPlano, true));
                });

        } else {

            loadEstadosModoIntermitenteOuApagado();
        }
    }

    private void loadEstadosEntradaDemandaPrioritaria() {
        loadEstagioPosModoIntermitente(estagio.getTempoVerdeDemandaPrioritaria() * 1000L);
    }

    private void loadEstadosModoIntermitenteOuApagado() {
        RangeMap<Long, EstadoGrupoSemaforico> intervaloVeicular = TreeRangeMap.create();
        intervaloVeicular.put(Range.closedOpen(0L, verde.getDuracao()), AMARELO_INTERMITENTE);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloPedestre = TreeRangeMap.create();
        intervaloPedestre.put(Range.closedOpen(0L, verde.getDuracao()), DESLIGADO);

        plano.getGruposSemaforicosPlanos().stream()
            .forEach(grupoSemaforicoPlano -> {
                final GrupoSemaforico grupo = grupoSemaforicoPlano.getGrupoSemaforico();
                if (grupo.isVeicular() && plano.isIntermitente()) {
                    estados.put(grupo.getPosicao(), intervaloVeicular);
                } else {
                    estados.put(grupo.getPosicao(), intervaloPedestre);
                }
            });
    }

    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
        return loadGrupoSemaforico(grupoSemaforico, false);
    }

    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        return loadGrupoSemaforico(grupoSemaforicoPlano, false);
    }

    //Ganho direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforico grupoSemaforico,
                                                                      boolean modoIntermitenteOuApagado) {
        RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGrupoSemaforicoPlano(grupoSemaforico);

        final long tempoInicial = 0L;
        EstadoGrupoSemaforico estadoFinal = VERDE;

        if (energizacaoGrupo(grupoSemaforico)) {
            intervalos.put(Range.closedOpen(tempoInicial, TEMPO_INTERMITENTE), grupoSemaforico.isVeicular() ?
                AMARELO_INTERMITENTE : DESLIGADO);

            intervalos.put(Range.closedOpen(TEMPO_INTERMITENTE, duracaoEntreverde), VERMELHO);

        } else {
            //Remover caso passe nos testes
            if (!grupoSemaforicoPlano.isAtivado() && !desenergizacaoGrupo(grupoSemaforico)) {
                intervalos.put(Range.closedOpen(tempoInicial, duracaoEntreverde), DESLIGADO);
                estadoFinal = DESLIGADO;

            } else {
                final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
                if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico) && !desenergizacaoGrupo(grupoSemaforico)) {
                    intervalos.put(Range.closedOpen(tempoInicial, duracaoEntreverde), VERDE);
                } else {
                    final Estagio estagioAtual = estagioPlano.getEstagio();

                    Transicao transicao = grupoSemaforico.findTransicaoComGanhoDePassagemByOrigemDestino(estagioAnterior, estagioAtual);

                    long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;
                    if (entreverde != null && entreverde.getDiffEntreVerde() > 0L && duracaoEntreverde > 0L) {
                        int atraso = (((Long) duracaoEntreverde).intValue() / 1000) -
                            plano.getTempoEntreVerdeQueConflitaComGrupoSemaforico(estagioAtual, estagioAnterior, grupoSemaforico);
                        if (atraso > 0) {
                            tempoAtraso = atraso * 1000L;
                        } else if (atraso == 0) {
                            Transicao proximaTransicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAtual,
                                estagioPlano.getEstagioPlanoProximo(plano.getEstagiosOrdenados()).getEstagio());

                            if (proximaTransicao != null) {
                                proximaTransicao.getAtrasoDeGrupo().setAtrasoDeGrupo(entreverde.getDiffEntreVerde().intValue() / 1000);
                            }
                        }
                    }


                    if (duracaoEntreverde > tempoAtraso) {
                        intervalos.put(Range.closedOpen(tempoInicial, duracaoEntreverde - tempoAtraso), VERMELHO);
                        intervalos.put(Range.closedOpen(duracaoEntreverde - tempoAtraso, duracaoEntreverde), VERDE);
                    } else {
                        intervalos.put(Range.closedOpen(tempoInicial, duracaoEntreverde), VERMELHO);
                    }
                }

            }
        }

        if (desenergizacaoGrupo(grupoSemaforico)) {
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + TEMPO_VERMELHO_INTEGRAL), VERMELHO);
            intervalos.put(Range.closedOpen(duracaoEntreverde + TEMPO_VERMELHO_INTEGRAL, duracaoEntreverde + duracaoVerde), DESLIGADO);
        } else {
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), estadoFinal);
        }


        return intervalos;
    }

    //Perdendo o direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano,
                                                                      boolean modoIntermitenteOuApagado) {
        RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();
        final GrupoSemaforico grupoSemaforico = grupoSemaforicoPlano.getGrupoSemaforico();

        EstadoGrupoSemaforico estadoFinal;

        long tempoInicial = 0L;

        if (energizacaoGrupo(grupoSemaforico)) {
            intervalos.put(Range.closedOpen(tempoInicial, TEMPO_INTERMITENTE), grupoSemaforico.isVeicular() ?
                AMARELO_INTERMITENTE : DESLIGADO);

            intervalos.put(Range.closedOpen(TEMPO_INTERMITENTE, duracaoEntreverde), VERMELHO);

            estadoFinal = VERMELHO;
        } else {

            if (!grupoSemaforicoPlano.isAtivado() && !desenergizacaoGrupo(grupoSemaforico)) {
                intervalos.put(Range.closedOpen(0L, duracaoEntreverde), DESLIGADO);
                estadoFinal = DESLIGADO;

            } else {
                final long tempoVermelhoIntegral;
                if (modoIntermitenteOuApagado) {
                    tempoVermelhoIntegral = 3000L;
                } else {
                    tempoVermelhoIntegral = 0L;
                }


                if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
                    final Transicao transicao;
                    long tempoAtraso;
                    if (estagio.getId() == null || desenergizacaoGrupo(grupoSemaforico)) {
                        transicao = grupoSemaforico.findTransicaoByDestinoIntermitente(estagioAnterior);
                        tempoAtraso = 0L;
                    } else {
                        transicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAnterior, estagio);
                        tempoAtraso = Math.min(transicao.getTempoAtrasoGrupo() * 1000L, duracaoEntreverde);
                    }

                    final TabelaEntreVerdesTransicao tabelaEntreVerdes = grupoSemaforico.findTabelaEntreVerdesTransicaoByTransicao(plano.getPosicaoTabelaEntreVerde(), transicao);
                    long tempoAmarelo;
                    final EstadoGrupoSemaforico estadoAmarelo;
                    final long vermelhoLimpeza = tabelaEntreVerdes.getTempoVermelhoLimpeza() * 1000L;

                    if (grupoSemaforico.isPedestre()) {
                        tempoAmarelo = tabelaEntreVerdes.getTempoVermelhoIntermitente() * 1000L;
                        estadoAmarelo = VERMELHO_INTERMITENTE;
                    } else {
                        tempoAmarelo = tabelaEntreVerdes.getTempoAmarelo() * 1000L;
                        estadoAmarelo = AMARELO;
                    }

                    if (tempoAtraso == 0 && (tempoAmarelo + vermelhoLimpeza) < (duracaoEntreverdeSemAtraso - tempoVermelhoIntegral)) {
                        tempoAtraso = Math.max(tempoAtraso, ((duracaoEntreverdeSemAtraso - tempoVermelhoIntegral) - (tempoAmarelo + vermelhoLimpeza)));
                    }

                    tempoAmarelo = Math.min(tempoAmarelo + tempoAtraso, duracaoEntreverde - tempoVermelhoIntegral);

                    intervalos.put(Range.closedOpen(0L, tempoAtraso), VERDE);
                    intervalos.put(Range.closedOpen(tempoAtraso, tempoAmarelo), estadoAmarelo);


                    if ((duracaoEntreverde - tempoVermelhoIntegral) > (tempoAmarelo + vermelhoLimpeza)) {
                        intervalos.put(Range.closedOpen(tempoAmarelo, tempoAmarelo + vermelhoLimpeza), EstadoGrupoSemaforico.VERMELHO_LIMPEZA);
                        intervalos.put(Range.closedOpen(tempoAmarelo + vermelhoLimpeza, duracaoEntreverde - tempoVermelhoIntegral), VERMELHO);
                    } else {
                        intervalos.put(Range.closedOpen(tempoAmarelo, duracaoEntreverde - tempoVermelhoIntegral), EstadoGrupoSemaforico.VERMELHO_LIMPEZA);
                    }

                } else {
                    intervalos.put(Range.closedOpen(0L, duracaoEntreverde - tempoVermelhoIntegral), VERMELHO);
                }


                if (modoIntermitenteOuApagado) {
                    intervalos.put(Range.closedOpen(duracaoEntreverde - tempoVermelhoIntegral, duracaoEntreverde), VERMELHO);
                    if (plano.isIntermitente() && grupoSemaforico.isVeicular()) {
                        estadoFinal = AMARELO_INTERMITENTE;
                    } else {
                        estadoFinal = DESLIGADO;
                    }
                } else {
                    estadoFinal = VERMELHO;
                }
            }
        }

        if (desenergizacaoGrupo(grupoSemaforico)) {
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + TEMPO_VERMELHO_INTEGRAL), VERMELHO);
            intervalos.put(Range.closedOpen(duracaoEntreverde + TEMPO_VERMELHO_INTEGRAL, duracaoEntreverde + duracaoVerde), DESLIGADO);

        } else {
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), estadoFinal);
        }



        return intervalos;
    }


    public EstagioPlano getEstagioPlano() {
        return estagioPlano;
    }

    public Estagio getEstagio() {
        return this.estagio;
    }

    public HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> getEstados() {
        return estados;
    }

    public ObjectNode toJson(DateTime timeStamp) {
        ObjectNode root = Json.newObject();
        root.put("w", duracao);
        root.put("x", timeStamp.getMillis());
        root.put("estagio", estagioPlano.getEstagio().getPosicao());
        ObjectNode grupos = root.putObject("grupos");


        estados.keySet().stream().forEach(key -> {
            ArrayNode grupo = grupos.putArray(key.toString());

            estados.get(key).asMapOfRanges().entrySet().stream().forEach(entry -> {
                ArrayNode fields = grupo.addArray();
                fields.add(entry.getKey().lowerEndpoint().toString());
                fields.add(entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                fields.add(entry.getValue().toString());
            });
        });

        ArrayNode eventos = root.putArray("eventos");

        if (entreverde != null) {
            parseEventos(entreverde, eventos);
        }

        if (verde != null) {
            parseEventos(verde, eventos);
        }

        return root;
    }

    private void parseEventos(IntervaloEstagio intervalo, ArrayNode eventos) {
        intervalo.getEventos().entrySet().stream().forEach(entry -> {
            entry.getValue().stream().forEach(eventoMotor -> {
                switch (eventoMotor.getTipoEvento()) {
                    case ACIONAMENTO_DETECTOR_PEDESTRE:
                        parseEventoDetector(eventoMotor, entry, eventos.addArray());
                        break;
                    case ACIONAMENTO_DETECTOR_VEICULAR:
                        parseEventoDetector(eventoMotor, entry, eventos.addArray());
                        break;
                    case TROCA_DE_PLANO_NO_ANEL:
                        parseEventoTrocaPlano(eventoMotor, entry, eventos.addArray());
                        break;
                    case INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL:
                        parseEventoGenerico(eventoMotor, entry, eventos.addArray());
                        break;
                    case RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL:
                        break;
                    default:
                        parseEventoGenerico(eventoMotor, entry, eventos.addArray());
                        break;

                }
            });
        });
    }

    private void parseEventoGenerico(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry, ArrayNode fields) {
        fields.add(entry.getKey());
        fields.add(eventoMotor.getTipoEvento().toString());
        fields.add(eventoMotor.getTipoEvento().getCodigo());
        fields.add(eventoMotor.getTipoEvento().getTipoEventoControlador().toString());
        fields.add(eventoMotor.getTipoEvento().getMessage(eventoMotor.getStringParams()));
    }

    private void parseEventoTrocaPlano(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry, ArrayNode fields) {
        fields.add(entry.getKey());
        fields.add(eventoMotor.getTipoEvento().toString());
        fields.add(eventoMotor.getParams()[0].toString());
        fields.add(eventoMotor.getParams()[1].toString());
        fields.add(((DateTime) eventoMotor.getParams()[2]).getMillis());
        fields.add(((DateTime) eventoMotor.getParams()[3]).getMillis());
    }

    private void parseEventoDetector(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry, ArrayNode fields) {
        fields.add(entry.getKey());
        fields.add(eventoMotor.getTipoEvento().toString());
        fields.add(((Pair<Integer, TipoDetector>) eventoMotor.getParams()[0]).getFirst());
    }

    public IntervaloEstagio getEntreverde() {
        return entreverde;
    }

    public IntervaloEstagio getVerde() {
        return verde;
    }

    public long getDuracao() {
        return duracao;
    }

    public Integer quantidadeGruposSemaforicos() {
        return estados.keySet().size();
    }

    private boolean energizacaoGrupo(GrupoSemaforico grupoSemaforico) {
        EstagioPlano origem = estagioPlanoAnterior;
        EstagioPlano destino = estagioPlano;
        if (origem == null || destino == null || origem.getPlano().equals(destino.getPlano()) || origem.getPlano().isApagada()) {
            return false;
        }

        return origem.getPlano()
            .getGruposSemaforicosPlanos()
            .stream()
            .anyMatch(gp -> !gp.isAtivado() && gp.getGrupoSemaforico().equals(grupoSemaforico) && destino.getPlano()
                .getGruposSemaforicosPlanos()
                .stream()
                .anyMatch(gp2 -> gp2.isAtivado() && gp2.getGrupoSemaforico().equals(grupoSemaforico)));
    }

    private boolean desenergizacaoGrupo(GrupoSemaforico grupoSemaforico) {
        EstagioPlano origem = estagioPlanoAnterior;
        EstagioPlano destino = estagioPlano;

        if (origem == null || destino == null || origem.getPlano().equals(destino.getPlano()) || destino.getPlano().isApagada()) {
            return false;
        }

        return origem.getPlano()
            .getGruposSemaforicosPlanos()
            .stream()
            .anyMatch(gp -> gp.isAtivado() && gp.getGrupoSemaforico().equals(grupoSemaforico) && destino.getPlano()
                .getGruposSemaforicosPlanos()
                .stream()
                .anyMatch(gp2 -> !gp2.isAtivado() && gp2.getGrupoSemaforico().equals(grupoSemaforico)));
    }

    private boolean energizacaoGrupo(EstagioPlano origem, EstagioPlano destino) {
        if (origem == null || destino == null || origem.getPlano().equals(destino.getPlano())) {
            return false;
        }

        return origem.getPlano()
            .getGruposSemaforicosPlanos()
            .stream()
            .anyMatch(gp -> !gp.isAtivado() && destino.getPlano()
                .getGruposSemaforicosPlanos()
                .stream()
                .anyMatch(gp2 -> gp2.isAtivado() && gp2.getGrupoSemaforico().equals(gp.getGrupoSemaforico())));
    }
}

package engine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import play.libs.Json;

import java.util.*;

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

    private HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> estados;

    public IntervaloGrupoSemaforico(IntervaloEstagio entreverde, IntervaloEstagio verde) {
        this.duracaoEntreverde = (entreverde != null ? entreverde.getDuracao() : 0L);
        this.duracaoVerde = verde.getDuracao();
        this.duracao = this.duracaoEntreverde + this.duracaoVerde;
        this.entreverde = entreverde;
        this.verde = verde;
        this.estagioPlano = verde.getEstagioPlano();
        this.estagio = this.estagioPlano.getEstagio();
        this.estagioPlanoAnterior = verde.getEstagioPlanoAnterior();
        if (this.estagioPlanoAnterior != null) {
            this.estagioAnterior = this.estagioPlanoAnterior.getEstagio();
        } else {
            this.estagioAnterior = null;
        }
        this.plano = estagioPlano.getPlano();
        if (plano.isIntermitente() || plano.isApagada()) {
            loadEstadosFixos();
        } else {
            loadEstados();
        }
    }

    private void loadEstados() {
        estados = new HashMap<>();

        if (estagioPlanoAnterior != null &&
            !estagioPlanoAnterior.getPlano().isModoOperacaoVerde()) {

            if (estagioPlanoAnterior.getPlano().isIntermitente()) {
                loadEstagioPosModoIntermitente(estagioPlano.getTempoVerdeEstagio());
            } else {
                loadEstagioSequenciaPartida(estagioPlano.getTempoVerdeEstagio());
            }


        } else {
            estagio.getGruposSemaforicos().forEach(grupoSemaforico -> {
                estados.put(grupoSemaforico.getPosicao(), loadGrupoSemaforico(grupoSemaforico));
            });

            plano.getGruposSemaforicosPlanos().stream()
                .filter(grupoSemaforicoPlano -> !estagio.getGruposSemaforicos().contains(grupoSemaforicoPlano.getGrupoSemaforico()))
                .forEach(grupoSemaforicoPlano -> {
                    estados.put(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao(), loadGrupoSemaforico(grupoSemaforicoPlano));
                });
        }

    }

    private void loadEstagioSequenciaPartida(Integer tempoVerdeEstagio) {
        final long tempoVerde = 8000L + (tempoVerdeEstagio * 1000L);

        plano.getGruposSemaforicosPlanos().stream()
            .forEach(grupoSemaforicoPlano -> {
                final GrupoSemaforico grupo = grupoSemaforicoPlano.getGrupoSemaforico();
                RangeMap<Long, EstadoGrupoSemaforico> intervalo = TreeRangeMap.create();

                if (grupo.isVeicular()) {
                    intervalo.put(Range.closedOpen(0L, 5000L), EstadoGrupoSemaforico.AMARELO_INTERMITENTE);
                } else {
                    intervalo.put(Range.closedOpen(0L, 5000L), EstadoGrupoSemaforico.DESLIGADO);
                }

                intervalo.put(Range.closedOpen(5000L, 8000L), EstadoGrupoSemaforico.VERMELHO);

                if (estagio.getGruposSemaforicos().contains(grupo)) {
                    intervalo.put(Range.closedOpen(8000L, tempoVerde), EstadoGrupoSemaforico.VERDE);
                } else {
                    intervalo.put(Range.closedOpen(8000L, tempoVerde), EstadoGrupoSemaforico.VERMELHO);
                }

                estados.put(grupo.getPosicao(), intervalo);
            });
    }

    private void loadEstagioPosModoIntermitente(Integer tempoVerdeEstagio) {
        RangeMap<Long, EstadoGrupoSemaforico> intervaloVermelho = TreeRangeMap.create();
        intervaloVermelho.put(Range.closedOpen(0L, 3000L), EstadoGrupoSemaforico.VERMELHO);
        intervaloVermelho.put(Range.closedOpen(3000L, 3000L + (tempoVerdeEstagio * 1000L)), EstadoGrupoSemaforico.VERMELHO);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloVerde = TreeRangeMap.create();
        intervaloVerde.put(Range.closedOpen(0L, 3000L), EstadoGrupoSemaforico.VERMELHO);
        intervaloVerde.put(Range.closedOpen(3000L, 3000L + (tempoVerdeEstagio * 1000L)), EstadoGrupoSemaforico.VERDE);

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
        loadEstagioPosModoIntermitente(estagio.getTempoVerdeDemandaPrioritaria());
    }

    private void loadEstadosModoIntermitenteOuApagado() {
        RangeMap<Long, EstadoGrupoSemaforico> intervaloVeicular = TreeRangeMap.create();
        intervaloVeicular.put(Range.closedOpen(0L, 255000L), EstadoGrupoSemaforico.AMARELO_INTERMITENTE);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloPedestre = TreeRangeMap.create();
        intervaloPedestre.put(Range.closedOpen(0L, 255000L), EstadoGrupoSemaforico.DESLIGADO);

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

    //Ganho direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforico grupoSemaforico,
                                                                      boolean modoIntermitenteOuApagado) {
        RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGrupoSemaforicoPlano(grupoSemaforico);

        if (grupoSemaforicoPlano.isAtivado()) {
            final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
            if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
                intervalos.put(Range.closedOpen(0L, duracaoEntreverde), EstadoGrupoSemaforico.VERDE);
            } else {
                final Estagio estagioAtual = estagioPlano.getEstagio();
                final Transicao transicao = grupoSemaforico.findTransicaoComGanhoDePassagemByOrigemDestino(estagioAnterior, estagioAtual);
                final long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;

                intervalos.put(Range.closedOpen(0L, duracaoEntreverde - tempoAtraso), EstadoGrupoSemaforico.VERMELHO);
                intervalos.put(Range.closedOpen(duracaoEntreverde - tempoAtraso, duracaoEntreverde), EstadoGrupoSemaforico.VERDE);
            }

            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.VERDE);
        } else {
            intervalos.put(Range.closedOpen(0L, duracaoEntreverde), EstadoGrupoSemaforico.DESLIGADO);
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.DESLIGADO);
        }

        return intervalos;
    }

    //Perdendo o direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        return loadGrupoSemaforico(grupoSemaforicoPlano, false);
    }

    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano,
                                                                      boolean modoIntermitenteOuApagado) {
        RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();

        if (grupoSemaforicoPlano.isAtivado()) {
            final GrupoSemaforico grupoSemaforico = grupoSemaforicoPlano.getGrupoSemaforico();

            final long tempoVermelhoIntegral;
            if (modoIntermitenteOuApagado) {
                tempoVermelhoIntegral = 3000L;
            } else {
                tempoVermelhoIntegral = 0L;
            }

            if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
                final Transicao transicao;
                if (estagio.getId() != null) {
                    transicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAnterior, estagio);
                } else {
                    transicao = grupoSemaforico.findTransicaoByDestinoIntermitente(estagioAnterior);
                }

                final TabelaEntreVerdesTransicao tabelaEntreVerdes = grupoSemaforico.findTabelaEntreVerdesTransicaoByTransicao(plano.getPosicaoTabelaEntreVerde(), transicao);
                final long tempo;
                final long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;

                intervalos.put(Range.closedOpen(0L, tempoAtraso), EstadoGrupoSemaforico.VERDE);
                if (grupoSemaforico.isPedestre()) {
                    tempo = (tabelaEntreVerdes.getTempoVermelhoIntermitente() * 1000L) + tempoAtraso;
                    intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.VERMELHO_INTERMITENTE);
                } else {
                    tempo = (tabelaEntreVerdes.getTempoAmarelo() * 1000L) + tempoAtraso;
                    intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.AMARELO);
                }

                intervalos.put(Range.closedOpen(tempo, duracaoEntreverde - tempoVermelhoIntegral), EstadoGrupoSemaforico.VERMELHO_LIMPEZA);
            } else {
                intervalos.put(Range.closedOpen(0L, duracaoEntreverde - tempoVermelhoIntegral), EstadoGrupoSemaforico.VERMELHO);
            }

            if (modoIntermitenteOuApagado) {
                intervalos.put(Range.closedOpen(duracaoEntreverde - tempoVermelhoIntegral, duracaoEntreverde), EstadoGrupoSemaforico.VERMELHO);
                if (plano.isIntermitente() && grupoSemaforico.isVeicular()) {
                    intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.AMARELO_INTERMITENTE);
                } else {
                    intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.DESLIGADO);
                }
            } else {
                intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.VERMELHO);
            }
        } else {
            intervalos.put(Range.closedOpen(0L, duracaoEntreverde), EstadoGrupoSemaforico.DESLIGADO);
            intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.DESLIGADO);
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

        List<String> gruposBuffer = new ArrayList<String>();

        estados.keySet().stream().forEach(key -> {
            ArrayNode grupo = grupos.putArray(key.toString());

            StringBuffer sbGrupo = new StringBuffer("\"" + key + "\":[");

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

        parseEventos(verde, eventos);

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
                        parseEventoInsercaoPlug(eventoMotor, entry, eventos.addArray());
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
        fields.add(eventoMotor.getTipoEvento().getMessage(Arrays.toString(eventoMotor.getParams())));
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

    private void parseEventoInsercaoPlug(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry, ArrayNode fields) {
//        fields.add(entry.getKey());
//        fields.add(eventoMotor.getTipoEvento().toString());
//        fields.add(eventoMotor.getParams()[0].toString());
//        fields.add(eventoMotor.getParams()[1].toString());
//        fields.add(((DateTime)eventoMotor.getParams()[2]).getMillis());
//        fields.add(((DateTime)eventoMotor.getParams()[3]).getMillis());
    }

    public IntervaloEstagio getEntreverde() {
        return entreverde;
    }
}

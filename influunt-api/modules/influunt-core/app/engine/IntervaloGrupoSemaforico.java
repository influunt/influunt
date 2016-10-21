package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.asynchttpclient.ws.WebSocketUtils.getKey;

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
        if (plano.isIntermitente() || plano.isApagada()){
            loadEstadosFixos();
        } else {
            loadEstados();
        }
    }

    private void loadEstados() {
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

    private void loadEstadosFixos() {
        estados = new HashMap<>();
        if (estagio.isDemandaPrioritaria()) {

            loadEstadosEntradaDemandaPrioritaria();

        } else if (estagioPlanoAnterior != null &&
                estagioPlanoAnterior.getPlano().isModoOperacaoVerde()){

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
        RangeMap<Long, EstadoGrupoSemaforico> intervaloVermelho = TreeRangeMap.create();
        intervaloVermelho.put(Range.closedOpen(0L, 3000L), EstadoGrupoSemaforico.VERMELHO);
        intervaloVermelho.put(Range.closedOpen(3000L, 3000L + (estagio.getTempoVerdeDemandaPrioritaria() * 1000L)), EstadoGrupoSemaforico.VERMELHO);

        RangeMap<Long, EstadoGrupoSemaforico> intervaloVerde = TreeRangeMap.create();
        intervaloVerde.put(Range.closedOpen(0L, 3000L), EstadoGrupoSemaforico.VERMELHO);
        intervaloVerde.put(Range.closedOpen(3000L, 3000L + (estagio.getTempoVerdeDemandaPrioritaria() * 1000L)), EstadoGrupoSemaforico.VERDE);

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

    //Ganho direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
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
                final Transicao transicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAnterior, estagio);
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
                if (plano.isIntermitente() && grupoSemaforico.isVeicular()){
                    intervalos.put(Range.closedOpen(duracaoEntreverde, 255000L), EstadoGrupoSemaforico.AMARELO_INTERMITENTE);
                } else {
                    intervalos.put(Range.closedOpen(duracaoEntreverde, 255000L), EstadoGrupoSemaforico.DESLIGADO);
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

    public String toJson(DateTime timeStamp) {
        StringBuffer sb = new StringBuffer("{\"w\":");
        sb.append(duracao);
        sb.append(",\"x\":");
        sb.append(timeStamp.getMillis());
        sb.append(",\"estagio\":");
        sb.append(estagioPlano.getEstagio().getPosicao());
        sb.append(",\"grupos\":{");

        List<String> gruposBuffer = new ArrayList<String>();

        estados.keySet().stream().forEach(key -> {

            StringBuffer sbGrupo = new StringBuffer("\"" + key + "\":[");

            String buffer = estados.get(key).asMapOfRanges().entrySet().stream().map(entry -> {
                final Long w = entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint();
                return "["
                        .concat(entry.getKey().lowerEndpoint().toString())
                        .concat(",")
                        .concat(w.toString())
                        .concat(",\"").concat(entry.getValue().toString())
                        .concat("\"]");
            }).collect(Collectors.joining(","));

            sbGrupo.append(buffer);
            sbGrupo.append("]");
            gruposBuffer.add(sbGrupo.toString());
        });
        sb.append(gruposBuffer.stream().collect(Collectors.joining(",")));
        sb.append("}");
        sb.append(",\"eventos\":[");

        if(entreverde!=null) {
            sb.append(parseEventos(entreverde));
        }

        sb.append(parseEventos(verde));

        sb.append("]");

        sb.append("}");

        return sb.toString();
    }

    private String parseEventos(IntervaloEstagio intervalo) {
        return intervalo.getEventos().entrySet().stream().map(entry -> {
            StringBuffer sbInner = new StringBuffer();
            sbInner.append(entry.getValue().stream().map(eventoMotor -> {
                StringBuffer eventosSb = new StringBuffer("[");
                switch (eventoMotor.getTipoEvento()){
                    case ACIONAMENTO_DETECTOR_PEDESTRE:
                    case ACIONAMENTO_DETECTOR_VEICULAR:
                        eventosSb.append(parseEventoDetector(eventoMotor,entry));
                         break;
                    case TROCA_DE_PLANO_NO_ANEL:
                        eventosSb.append(parseEventoTrocaPlano(eventoMotor,entry));
                        break;

                }
                return eventosSb.toString();
            }).collect(Collectors.joining(",")));
            return sbInner.toString();
        }).collect(Collectors.joining(","));
    }

    private String parseEventoTrocaPlano(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry) {
        StringBuffer sb = new StringBuffer();
        sb.append(entry.getKey());
        sb.append(",\"");
        sb.append(eventoMotor.getTipoEvento().toString());
        sb.append("\",");
        sb.append(eventoMotor.getParams()[0].toString());
        sb.append(",");
        sb.append(eventoMotor.getParams()[1].toString());
        sb.append(",");
        sb.append(((DateTime)eventoMotor.getParams()[2]).getMillis());
        sb.append(",");
        sb.append(((DateTime)eventoMotor.getParams()[3]).getMillis());

        sb.append("]");

        return sb.toString();
    }

    private String parseEventoDetector(EventoMotor eventoMotor, Map.Entry<Long, List<EventoMotor>> entry) {
        StringBuffer sb = new StringBuffer();
        sb.append(entry.getKey());
        sb.append(",\"");
        sb.append(eventoMotor.getTipoEvento().toString());
        sb.append("\",");
        sb.append(((Detector)eventoMotor.getParams()[0]).getPosicao());
        sb.append("]");

        return sb.toString();
    }

}

package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IntervaloGrupoSemaforico {


    private final IntervaloEstagio entreverde;

    private final long duracao;

    private final EstagioPlano estagioPlano;

    private final EstagioPlano estagioPlanoAnterior;

    private final IntervaloEstagio verde;

    private final long duracaoEntreverde;

    private final long duracaoVerde;

    public IntervaloGrupoSemaforico(IntervaloEstagio entreverde, IntervaloEstagio verde) {
        this.duracaoEntreverde = (entreverde != null ? entreverde.getDuracao() : 0L);
        this.duracaoVerde = verde.getDuracao();
        this.duracao =  this.duracaoEntreverde + this.duracaoVerde;
        this.entreverde = entreverde;
        this.verde = verde;
        this.estagioPlano = verde.getEstagioPlano();
        this.estagioPlanoAnterior = verde.getEstagioPlanoAnterior();
        loadEstados();
        System.out.println(estados);
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


        final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
        if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico)) {
            intervalos.put(Range.closedOpen(0L, duracaoEntreverde), EstadoGrupoSemaforico.VERDE);
        } else {
            final Estagio estagioAtual = estagioPlano.getEstagio();
            final Transicao transicao = grupoSemaforico.findTransicaoComGanhoDePassagemByOrigemDestino(estagioAnterior, estagioAtual);
            final long tempoAtraso = transicao.getTempoAtrasoGrupo() * 1000L;
            intervalos.put(Range.closedOpen(0L, tempoAtraso), EstadoGrupoSemaforico.VERDE);
            intervalos.put(Range.closedOpen(tempoAtraso, duracaoEntreverde), EstadoGrupoSemaforico.VERMELHO);
        }

        intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.VERDE);

        return intervalos;
    }

    //Perdendo o direito de passagem
    private RangeMap<Long, EstadoGrupoSemaforico> loadGrupoSemaforico(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        RangeMap<Long, EstadoGrupoSemaforico> intervalos = TreeRangeMap.create();

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
                tempo = (tabelaEntreVerdes.getTempoVermelhoIntermitente() * 1000L) + tempoAtraso;
                intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.VERMELHO_INTERMITENTE);
            } else {
                tempo = (tabelaEntreVerdes.getTempoAmarelo() * 1000L) + tempoAtraso;
                intervalos.put(Range.closedOpen(tempoAtraso, tempo), EstadoGrupoSemaforico.AMARELO);
            }
            intervalos.put(Range.closedOpen(tempo, duracaoEntreverde), EstadoGrupoSemaforico.VERMELHO_LIMPEZA);
        } else {
            intervalos.put(Range.closedOpen(0L, duracaoEntreverde), EstadoGrupoSemaforico.VERMELHO);
        }

        intervalos.put(Range.closedOpen(duracaoEntreverde, duracaoEntreverde + duracaoVerde), EstadoGrupoSemaforico.VERMELHO);

        return intervalos;
    }

    public long getDuracao() {
        return duracao;
    }

    public EstagioPlano getEstagioPlano() {
        return estagioPlano;
    }

    public Estagio getEstagio() {
        return this.estagioPlano.getEstagio();
    }

    private HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> estados;

    public HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> getEstados() {
        return estados;
    }

    public List<EstadoGrupoSemaforico> getEstadoGrupoSemaforicos(long instante) {
        return estados.keySet()
                .stream()
                .sorted()
                .map(i -> estados.get(i).get(instante))
                .collect(Collectors.toList());
    }
}

package engine.eventos;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.TipoEvento;
import models.*;
import org.apache.commons.math3.util.Pair;

import java.util.Map;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class DetectorVeicularHandle extends GerenciadorDeEventos {
    private final int contadorEstagio;

    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public DetectorVeicularHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorEstagio = gerenciadorDeEstagios.getContadorEstagio();
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Pair<Integer, TipoDetector> key = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];

        Detector detector = gerenciadorDeEstagios.getDetector(key.getFirst(), key.getSecond());

        if (detector.isComFalha()) {
            gerenciadorDeEstagios.onEvento(new EventoMotor(gerenciadorDeEstagios.getTimestamp(),
                TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR,
                key,
                detector.getAnel().getPosicao()));
        }

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
        reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo, contadorDeCiclos);
    }

    private void adicionaEstagioDemandaPrioritaria(Estagio estagio) {
        final EstagioPlano estagioPlanoExistente = listaEstagioPlanos.stream().filter(estagioPlano -> estagioPlano.getEstagio().equals(estagio)).findFirst().orElse(null);
        if (estagioPlanoExistente == null || (listaEstagioPlanos.indexOf(estagioPlanoExistente) < contadorEstagio)) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setEstagio(estagio);
            estagioPlano.setTempoVerde(estagio.getTempoVerdeDemandaPrioritaria());
            if (plano.isModoOperacaoVerde()) {
                estagioPlano.setPlano(plano);
            } else {
                Plano novoPlano = new Plano();
                novoPlano.setEstagiosPlanos(plano.getEstagiosPlanos());
                novoPlano.setGruposSemaforicosPlanos(plano.getGruposSemaforicosPlanos());
                novoPlano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
                estagioPlano.setPlano(novoPlano);
            }

            final EstagioPlano estagioPlanoAnterior = proximoEstagioPlanoNaoProibido(estagio, estagioPlanoAtual);

            if (plano.isManual()) {
                listaEstagioPlanos.clear();
                if (!estagioPlanoAtual.equals(estagioPlanoAnterior)) {
                    listaEstagioPlanos.add(estagioPlanoAtual);
                }
                listaEstagioPlanos.add(estagioPlanoAnterior);
                listaEstagioPlanos.add(estagioPlano);
                gerenciadorDeEstagios.reiniciaContadorEstagio();
            } else {
                final int index = listaEstagioPlanos.indexOf(estagioPlanoAnterior);
                listaEstagioPlanos.add(index + 1, estagioPlano);
            }
        }
    }

    private EstagioPlano proximoEstagioPlanoNaoProibido(Estagio estagioPrioritario, EstagioPlano estagioPlanoAtual) {
        if (estagioPlanoAtual.getEstagio().temTransicaoProibidaParaEstagio(estagioPrioritario)) {
            final EstagioPlano proximoEstagioPlano = estagioPlanoAtual.getEstagioPlanoProximo(listaEstagioPlanos);
            return proximoEstagioPlanoNaoProibido(estagioPrioritario, proximoEstagioPlano);
        } else {
            return estagioPlanoAtual;
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

    private void aumentarTempoEstagioAtual(EstagioPlano estagioPlano) {
        Map.Entry<Range<Long>, IntervaloEstagio> range = this.intervalos.getEntry(contadorIntervalo);
        final long tempoExtensao = ((long) (estagioPlano.getTempoExtensaoVerde() * 1000L));
        final long tempoMaximo = estagioPlano.getTempoVerdeMaximo() * 1000L;

        final IntervaloEstagio intervalo = range.getValue();

        final long tempoAdicionado = (contadorIntervalo + tempoExtensao) - range.getKey().upperEndpoint();

        if (tempoAdicionado > 0) {
            long novaDuracao = intervalo.getDuracao() + tempoAdicionado;
            if (novaDuracao > tempoMaximo) {
                novaDuracao = tempoMaximo;
            }

            this.intervalos.remove(range.getKey());
            intervalo.setDuracao(novaDuracao);
            this.intervalos.put(Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + novaDuracao), intervalo);
        }

    }
}

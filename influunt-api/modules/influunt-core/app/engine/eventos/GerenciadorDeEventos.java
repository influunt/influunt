package engine.eventos;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import models.EstagioPlano;
import models.Plano;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 10/24/16.
 */
public abstract class GerenciadorDeEventos {
    protected final Plano plano;

    protected final EstagioPlano estagioPlanoAtual;

    protected final List<EstagioPlano> listaEstagioPlanos;

    protected final GerenciadorDeEstagios gerenciadorDeEstagios;

    public GerenciadorDeEventos(GerenciadorDeEstagios gerenciadorDeEstagios) {
        this.gerenciadorDeEstagios = gerenciadorDeEstagios;
        this.plano = gerenciadorDeEstagios.getPlano();
        this.estagioPlanoAtual = gerenciadorDeEstagios.getEstagioPlanoAtual();
        this.listaEstagioPlanos = gerenciadorDeEstagios.getListaEstagioPlanos();
    }

    public static void onEvento(GerenciadorDeEstagios gerenciadorDeEstagios, EventoMotor eventoMotor) {
        switch (eventoMotor.getTipoEvento()) {
            case ACIONAMENTO_DETECTOR_PEDESTRE:
                new DetectorPedestreHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case ACIONAMENTO_DETECTOR_VEICULAR:
                new DetectorVeicularHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL:
                new AtivaModoManualHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL:
                new DesativaModoManualHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case TROCA_ESTAGIO_MANUAL:
                new TrocaEstagioManualHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_SEQUENCIA_DE_CORES:
                new ImporAmareloIntermitenteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO:
            case FALHA_VERDES_CONFLITANTES_REMOCAO:
                new RemoverAmareloIntermitenteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
                new FalhaDetectorVeicularHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_VERDES_CONFLITANTES:
                new VerdeConflitanteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;

            default:
                break;
        }
    }

    protected abstract void processar(EventoMotor eventoMotor);

    protected void reduzirTempoEstagio(EstagioPlano estagioPlanoAnterior,
                                       RangeMap<Long, IntervaloEstagio> intervalos,
                                       long contadorIntervalo) {
        final long contador;
        if (intervalos.get(contadorIntervalo) != null) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = intervalos.getEntry(contadorIntervalo);
            IntervaloEstagio intervalo = range.getValue();
            if (intervalo.isEntreverde()) {
                range = intervalos.getEntry(range.getKey().upperEndpoint() + 1);
                intervalo = range.getValue();
                contador = 0L;
            } else {
                contador = contadorIntervalo - range.getKey().lowerEndpoint();
            }
            long duracao = Math.max(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior), contador);
            intervalo.setDuracao(duracao);
            intervalos.remove(range.getKey());
            final Range<Long> novoRange = Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + duracao);
            intervalos.put(novoRange, intervalo);
        }
    }

    protected void terminaTempoEstagio(EstagioPlano estagioPlanoAnterior,
                                       RangeMap<Long, IntervaloEstagio> intervalos,
                                       long contadorIntervalo) {
        if (intervalos.get(contadorIntervalo) != null) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = intervalos.getEntry(contadorIntervalo);
            IntervaloEstagio intervalo = range.getValue();

            if (intervalo.isEntreverde()) {
                intervalos.remove(range.getKey());
                range = intervalos.getEntry(range.getKey().upperEndpoint() + 1);
                intervalo = intervalos.get(range.getKey().lowerEndpoint());
            }

            intervalo.setDuracao(0);
            intervalos.remove(range.getKey());
            final Range<Long> novoRange = Range.closedOpen(0L, 100L);
            intervalos.put(novoRange, intervalo);
        }
    }
}

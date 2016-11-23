package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.GerenciadorDeEstagiosHelper;
import engine.IntervaloEstagio;
import models.EstagioPlano;
import models.Plano;

import java.util.List;

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
            case IMPOSICAO_PLANO:
                new ImporPlanoHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case IMPOSICAO_MODO:
                new ImporModoHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case LIBERAR_IMPOSICAO:
                new LiberarImposicaoHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_SEQUENCIA_DE_CORES:
            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
            case FALHA_WATCH_DOG:
            case FALHA_MEMORIA:
                new ImporAmareloIntermitenteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO:
            case REMOCAO_FALHA_VERDES_CONFLITANTES:
                new RemoverAmareloIntermitenteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                new FalhaDetectorHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case FALHA_VERDES_CONFLITANTES:
                new VerdeConflitanteHandle(gerenciadorDeEstagios).processar(eventoMotor);
                break;
            case REMOCAO_FALHA_DETECTOR_PEDESTRE:
            case REMOCAO_FALHA_DETECTOR_VEICULAR:
                new RemoverFalhaDetectorHandle(gerenciadorDeEstagios).processar(eventoMotor);
            default:
                break;
        }
    }

    protected abstract void processar(EventoMotor eventoMotor);

    protected void reduzirTempoEstagio(EstagioPlano estagioPlanoAnterior,
                                       RangeMap<Long, IntervaloEstagio> intervalos,
                                       long contadorIntervalo) {
        GerenciadorDeEstagiosHelper.reduzirTempoEstagio(estagioPlanoAnterior, intervalos, contadorIntervalo, estagioPlanoAtual);
    }

    protected void terminaTempoEstagio(RangeMap<Long, IntervaloEstagio> intervalos,
                                       long contadorIntervalo) {
        GerenciadorDeEstagiosHelper.terminaTempoEstagio(intervalos, contadorIntervalo);
    }
}

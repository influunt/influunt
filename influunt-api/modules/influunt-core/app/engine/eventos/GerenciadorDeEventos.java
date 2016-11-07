package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import models.EstagioPlano;
import models.Plano;

import java.util.List;

import static engine.TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE;
import static engine.TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR;

/**
 * Created by rodrigosol on 10/24/16.
 */
public abstract class GerenciadorDeEventos {
    protected final Plano plano;
    protected final EstagioPlano estagioPlanoAtual;
    protected final List<EstagioPlano> listaEstagioPlanos;

    protected final GerenciadorDeEstagios gerenciadorDeEstagios;

    protected abstract void processar(EventoMotor eventoMotor);

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
            default:
                break;
        }
    }
}

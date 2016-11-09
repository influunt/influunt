package engine.eventos;

import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.services.PlanoService;
import models.Anel;
import models.Plano;

/**
 * Created by leonardo on 11/7/16.
 */
public class DesativaModoManualHandle extends GerenciadorDeEventos{
    @Override
    protected void processar(EventoMotor eventoMotor) {
        Anel anel = gerenciadorDeEstagios.getPlano().getAnel();
        if (anel.isAceitaModoManual()) {
            Plano plano = (Plano) eventoMotor.getParams()[1];
            gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp()));
        }
    }

    public DesativaModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }
}

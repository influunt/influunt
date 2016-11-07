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
public class AtivaModoManualHandle extends GerenciadorDeEventos{
    @Override
    protected void processar(EventoMotor eventoMotor) {
        Anel anel = gerenciadorDeEstagios.getPlano().getAnel();
        Plano plano = anel.getPlanos().stream().filter(Plano::isManual).findFirst().orElse(null);
        if (plano == null) {
            plano = PlanoService.gerarPlanoManual(gerenciadorDeEstagios.getPlano());
        }
        gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp()));
    }

    public AtivaModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }
}

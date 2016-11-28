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
public class AtivaModoManualHandle extends GerenciadorDeEventos {
    public AtivaModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Anel anel = gerenciadorDeEstagios.getPlano().getAnel();


        if (anel.isAceitaModoManual()) {
            Plano plano = anel.getPlanos().stream().filter(Plano::isManual).findFirst().orElse(null);
            if (plano == null && gerenciadorDeEstagios.getPlano().isModoOperacaoVerde()) {
                plano = PlanoService.gerarPlanoManual(gerenciadorDeEstagios.getPlano());
            }

            //TODO: Não entra no manual, se caso não tiver sido configurado o plano exclusivo e o plano vigente for Intermitente ou Apagado
            if (plano != null) {
                gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp()));
            }
        }
    }
}

package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.services.PlanoService;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class RemoverAmareloIntermitenteHandle extends GerenciadorDeEventos{
    @Override
    protected void processar(EventoMotor eventoMotor) {
        Plano plano = (Plano) eventoMotor.getParams()[1];
        this.plano.setImpostoPorFalha(false);
        gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp(), true));
    }

    public RemoverAmareloIntermitenteHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }
}

package services;

import helpers.ControladorUtil;
import models.*;

/**
 * Serviço do {@link Controlador}
 * <p>
 * Created by lesiopinheiro on 8/19/16.
 */
public class PlanoService {

    public static Plano gerarPlanoManual(Plano plano) {
        final ControladorUtil controladorUtil = new ControladorUtil();
        Plano novoPlano = controladorUtil.copyPrimitiveFields(plano);

        novoPlano.setVersaoPlano(plano.getVersaoPlano());
        novoPlano.setModoOperacao(ModoOperacaoPlano.MANUAL);

        plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
            GrupoSemaforicoPlano gspAux = controladorUtil.copyPrimitiveFields(grupoSemaforicoPlano);
            gspAux.setGrupoSemaforico(grupoSemaforicoPlano.getGrupoSemaforico());
            gspAux.setPlano(novoPlano);
            novoPlano.addGruposSemaforicoPlano(gspAux);
        });

        plano.getEstagiosPlanos().forEach(estagioPlano -> {
            EstagioPlano estagioPlanoAux = controladorUtil.copyPrimitiveFields(estagioPlano);
            estagioPlanoAux.setEstagio(estagioPlano.getEstagio());
            estagioPlanoAux.setPlano(novoPlano);
            novoPlano.addEstagios(estagioPlanoAux);
        });

        return novoPlano;
    }
}

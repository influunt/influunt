package engine.services;

import helpers.CloneHelper;
import models.EstagioPlano;
import models.GrupoSemaforicoPlano;
import models.ModoOperacaoPlano;
import models.Plano;

/**
 * Created by leonardo on 11/7/16.
 */
public class PlanoService {
    public static Plano gerarPlanoManual(Plano plano) {
        Plano novoPlano = gerarPlano(plano);

        plano.getEstagiosPlanos().forEach(estagioPlano -> {
            EstagioPlano estagioPlanoAux = CloneHelper.copyPrimitiveFields(estagioPlano);
            estagioPlanoAux.setEstagio(estagioPlano.getEstagio());
            estagioPlanoAux.setPlano(novoPlano);
            estagioPlanoAux.setDispensavel(false);
            novoPlano.addEstagios(estagioPlanoAux);
        });

        novoPlano.setModoOperacao(ModoOperacaoPlano.MANUAL);
        return novoPlano;
    }

    public static Plano gerarPlanoIntermitentePorFalha(Plano plano) {
        Plano novoPlano = gerarPlano(plano);
        novoPlano.setModoOperacao(ModoOperacaoPlano.INTERMITENTE);
        novoPlano.setImpostoPorFalha(true);
        return novoPlano;
    }

    private static Plano gerarPlano(Plano plano) {
        Plano novoPlano = CloneHelper.copyPrimitiveFields(plano);

        novoPlano.setVersaoPlano(plano.getVersaoPlano());

        plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
            GrupoSemaforicoPlano gspAux = CloneHelper.copyPrimitiveFields(grupoSemaforicoPlano);
            gspAux.setGrupoSemaforico(grupoSemaforicoPlano.getGrupoSemaforico());
            gspAux.setPlano(novoPlano);
            novoPlano.addGruposSemaforicoPlano(gspAux);
        });

        return novoPlano;
    }

    public static Plano gerarPlanoIntermitente(Plano plano) {
        Plano novoPlano = gerarPlano(plano);
        novoPlano.setModoOperacao(ModoOperacaoPlano.INTERMITENTE);
        novoPlano.setImposto(true);
        return novoPlano;
    }

    public static Plano gerarPlanoApagado(Plano plano) {
        Plano novoPlano = gerarPlano(plano);
        novoPlano.setModoOperacao(ModoOperacaoPlano.APAGADO);
        novoPlano.setImposto(true);
        return novoPlano;
    }
}

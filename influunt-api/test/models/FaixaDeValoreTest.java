package models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import config.WithInfluuntApplicationNoAuthentication;


/**
 * Created by juarezlustosa on 21/11/16.
 */
public class FaixaDeValoreTest extends WithInfluuntApplicationNoAuthentication {
    @Test
    public void validaValores() {
        FaixasDeValores faixasDeValores = FaixasDeValores.getInstance();
        faixasDeValores.save();

        assertEquals("0", faixasDeValores.getTempoDefasagemMin().toString());
        assertEquals("255", faixasDeValores.getTempoDefasagemMax().toString());

        assertEquals("3", faixasDeValores.getTempoAmareloMin().toString());
        assertEquals("5", faixasDeValores.getTempoAmareloMax().toString());

        assertEquals("3", faixasDeValores.getTempoVermelhoIntermitenteMin().toString());
        assertEquals("32", faixasDeValores.getTempoVermelhoIntermitenteMax().toString());

        assertEquals("0", faixasDeValores.getTempoVermelhoLimpezaVeicularMin().toString());
        assertEquals("20", faixasDeValores.getTempoVermelhoLimpezaVeicularMax().toString());

        assertEquals("0", faixasDeValores.getTempoVermelhoLimpezaPedestreMin().toString());
        assertEquals("5", faixasDeValores.getTempoVermelhoLimpezaPedestreMax().toString());

        assertEquals("0", faixasDeValores.getTempoAtrasoGrupoMin().toString());
        assertEquals("20", faixasDeValores.getTempoAtrasoGrupoMax().toString());

        assertEquals("10", faixasDeValores.getTempoVerdeSegurancaVeicularMin().toString());
        assertEquals("30", faixasDeValores.getTempoVerdeSegurancaVeicularMax().toString());

        assertEquals("4", faixasDeValores.getTempoVerdeSegurancaPedestreMin().toString());
        assertEquals("10", faixasDeValores.getTempoVerdeSegurancaPedestreMax().toString());

        assertEquals("20", faixasDeValores.getTempoMaximoPermanenciaEstagioMin().toString());
        assertEquals("255", faixasDeValores.getTempoMaximoPermanenciaEstagioMax().toString());

        assertEquals("127", faixasDeValores.getDefaultTempoMaximoPermanenciaEstagioVeicular().toString());

        assertEquals("30", faixasDeValores.getTempoCicloMin().toString());
        assertEquals("255", faixasDeValores.getTempoCicloMax().toString());

        assertEquals("10", faixasDeValores.getTempoVerdeMinimoMin().toString());
        assertEquals("255", faixasDeValores.getTempoVerdeMinimoMax().toString());

        assertEquals("10", faixasDeValores.getTempoVerdeIntermediarioMin().toString());
        assertEquals("255", faixasDeValores.getTempoVerdeIntermediarioMax().toString());

        assertEquals("1.0", faixasDeValores.getTempoExtensaoVerdeMin().toString());
        assertEquals("10.0", faixasDeValores.getTempoExtensaoVerdeMax().toString());

        assertEquals("1", faixasDeValores.getTempoVerdeMin().toString());
        assertEquals("255", faixasDeValores.getTempoVerdeMax().toString());

        assertEquals("0", faixasDeValores.getTempoAusenciaDeteccaoMin().toString());
        assertEquals("5800", faixasDeValores.getTempoAusenciaDeteccaoMax().toString());

        assertEquals("0", faixasDeValores.getTempoDeteccaoPermanenteMin().toString());
        assertEquals("10", faixasDeValores.getTempoDeteccaoPermanenteMax().toString());
    }
}

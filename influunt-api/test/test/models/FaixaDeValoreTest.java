package test.models;

import models.FaixasDeValores;
import org.junit.Test;
import test.config.WithInfluuntApplicationNoAuthentication;

import static org.junit.Assert.assertEquals;


/**
 * Created by juarezlustosa on 21/11/16.
 */
public class FaixaDeValoreTest extends WithInfluuntApplicationNoAuthentication {
    @Test
    public void validaValores() {
        FaixasDeValores faixasDeValores = FaixasDeValores.getInstance();
        faixasDeValores.save();
        String valorPadrao = "255";

        assertEquals("0", faixasDeValores.getTempoDefasagemMin().toString());
        assertEquals(valorPadrao, faixasDeValores.getTempoDefasagemMax().toString());

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
        assertEquals(valorPadrao, faixasDeValores.getTempoMaximoPermanenciaEstagioMax().toString());

        assertEquals("127", faixasDeValores.getDefaultTempoMaximoPermanenciaEstagioVeicular().toString());

        assertEquals("30", faixasDeValores.getTempoCicloMin().toString());
        assertEquals(valorPadrao, faixasDeValores.getTempoCicloMax().toString());

        assertEquals("10", faixasDeValores.getTempoVerdeMinimoMin().toString());
        assertEquals(valorPadrao, faixasDeValores.getTempoVerdeMinimoMax().toString());

        assertEquals("10", faixasDeValores.getTempoVerdeIntermediarioMin().toString());
        assertEquals(valorPadrao, faixasDeValores.getTempoVerdeIntermediarioMax().toString());

        assertEquals("1.0", faixasDeValores.getTempoExtensaoVerdeMin().toString());
        assertEquals("10.0", faixasDeValores.getTempoExtensaoVerdeMax().toString());

        assertEquals("1", faixasDeValores.getTempoVerdeMin().toString());
        assertEquals(valorPadrao, faixasDeValores.getTempoVerdeMax().toString());

        assertEquals("0", faixasDeValores.getTempoAusenciaDeteccaoMin().toString());
        assertEquals("1440", faixasDeValores.getTempoAusenciaDeteccaoMax().toString());

        assertEquals("0", faixasDeValores.getTempoDeteccaoPermanenteMin().toString());
        assertEquals("1440", faixasDeValores.getTempoDeteccaoPermanenteMax().toString());

        assertEquals("0", faixasDeValores.getTempoAusenciaDeteccaoPedestreMin().toString());
        assertEquals("5800", faixasDeValores.getTempoAusenciaDeteccaoPedestreMax().toString());

        assertEquals("0", faixasDeValores.getTempoDeteccaoPermanentePedestreMin().toString());
        assertEquals("10", faixasDeValores.getTempoDeteccaoPermanentePedestreMax().toString());
    }
}

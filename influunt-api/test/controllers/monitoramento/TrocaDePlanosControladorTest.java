package controllers.monitoramento;

import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import status.AlarmesFalhasControlador;
import status.TrocaDePlanoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
public class TrocaDePlanosControladorTest extends WithInfluuntApplicationNoAuthentication {

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {
        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        TrocaDePlanoControlador.jongo = jongo;

        jongo.getCollection(AlarmesFalhasControlador.COLLECTION).drop();

        TrocaDePlanoControlador.deleteAll();


        Plano plano = new Plano();
        plano.setModoOperacao(ModoOperacaoPlano.MANUAL);
        plano.setPosicao(1);
        plano.setDescricao("Plano Teste");
        TrocaDePlanoControlador.log(System.currentTimeMillis(),
            "1",
            "2",
            Json.toJson(new AgendamentoTrocaPlano(null, plano, new DateTime()))
        );

        Thread.sleep(10);

        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        AgendamentoTrocaPlano agendamento = new AgendamentoTrocaPlano(null, plano, new DateTime());
        agendamento.setImposicaoPlano(true);
        TrocaDePlanoControlador.log(System.currentTimeMillis(),
            "2",
            "1",
            Json.toJson(agendamento)
        );

        Thread.sleep(10);

        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        TrocaDePlanoControlador.log(System.currentTimeMillis(),
            "1",
            "2",
            Json.toJson(new AgendamentoTrocaPlano(null, plano, new DateTime()))
        );

        Thread.sleep(10);

        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        TrocaDePlanoControlador.log(System.currentTimeMillis(),
            "2",
            "1",
            Json.toJson(new AgendamentoTrocaPlano(null, plano, new DateTime()))
        );

        Thread.sleep(10);

        plano.setModoOperacao(ModoOperacaoPlano.INTERMITENTE);
        TrocaDePlanoControlador.log(System.currentTimeMillis(),
            "2",
            "1",
            Json.toJson(new AgendamentoTrocaPlano(null, plano, new DateTime(), true))
        );

    }

    @Test
    public void testUltimosModoOperacaoDosControladores() {
        HashMap<String, ModoOperacaoPlano> usc = TrocaDePlanoControlador.ultimoModoOperacaoDosControladores();

        assertEquals(2, usc.size());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, usc.get("1"));
        assertEquals(ModoOperacaoPlano.INTERMITENTE, usc.get("2"));
    }

    @Test
    public void testUltimoPlanoDeUmControlador() {
        assertEquals(ModoOperacaoPlano.INTERMITENTE, TrocaDePlanoControlador.ultimaTrocaDePlanoDoControlador("2").getModoOperacao());
        assertTrue(TrocaDePlanoControlador.ultimaTrocaDePlanoDoControlador("2").getTrocaDePlano().isImpostoPorFalha());
    }


}

package controllers;

import checks.Erro;
import checks.InfluuntValidator;
import checks.TabelaHorariosCheck;
import models.Controlador;
import models.Evento;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 8/31/16.
 */
public class EventosControllerTest extends AbstractInfluuntControladorTest {

    @Test
    public void deveriaDeletarEventoTabelaHoraria() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals("Total de Erros", 0, erros.size());

        int totalEventos = Evento.find.findRowCount();

        String idEvento = controlador.getTabelaHoraria().getEventos().get(0).getId().toString();


        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.EventosController.delete(idEvento).url());

        Result deleteResult = route(deleteRequest);
        assertEquals(OK, deleteResult.status());

        assertNull(Evento.find.byId(UUID.fromString(idEvento)));
        assertEquals("Quantidade de eventos", totalEventos - 1, Evento.find.findRowCount());

    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, TabelaHorariosCheck.class);
    }

}

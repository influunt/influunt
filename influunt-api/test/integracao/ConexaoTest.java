package integracao;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import status.StatusConexaoControlador;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ConexaoTest extends BasicMQTTTest {


    @Test
    public void centralDeveSeConectarAoServidorMQTT() {
        //A central ao se conectar no servidor deve se inscrever em diversos tópicos

        await().until(() -> StatusConexaoControlador.ultimoStatus(idControlador) != null);
        //A central conectou
        assertEquals("central", onConnectFutureList.get(0));

        assertEquals("central", onConnectFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica online
        assertEquals("controladores/conn/online", onSubscribeFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica offline
        assertEquals("controladores/conn/offline", onSubscribeFutureList.get(1));

        //A central se increveu para receber informação de echo
        assertEquals("central/+", onSubscribeFutureList.get(2));


        //A central se increveu para receber informação de echo
        assertEquals("controlador/" + idControlador + "/+", onSubscribeFutureList.get(3));

        //O cliente se conectou
        assertEquals(idControlador, onConnectFutureList.get(1));

        //O cliente envio a CONTROLADOR_ONLINE
        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(0)));

        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals("CONTROLADOR_ONLINE", json.get("tipoMensagem").asText());
        assertEquals("controladores/conn/online", json.get("destino").asText());
        assertTrue(json.has("carimboDeTempo"));
        assertTrue(json.has("idMensagem"));
        assertTrue(json.has("conteudo"));

        assertTrue(json.get("conteudo").has("dataHora"));
        assertTrue(json.get("conteudo").has("versao72c"));
        assertTrue(json.get("conteudo").has("status"));
        assertEquals("NOVO", json.get("conteudo").get("status").asText());


        //Verificar se o registro da conexao foi salvo
        StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(idControlador);
        assertTrue(status.isConectado());
        assertEquals(Long.valueOf(json.get("carimboDeTempo").asLong()), status.timestamp);

    }
}

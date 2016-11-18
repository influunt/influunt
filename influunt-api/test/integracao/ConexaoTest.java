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
        startClient();

        await().until(() -> StatusConexaoControlador.ultimoStatus(idControlador) != null);
        //A central conectou
        assertEquals("central", onConnectFutureList.get(0));

        assertEquals("central", onConnectFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica online
        assertEquals("controladores/conn/online", onSubscribeFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica offline
        assertEquals("controladores/conn/offline", onSubscribeFutureList.get(1));

        //A central se increveu para receber informação de central
        assertEquals("central/+", onSubscribeFutureList.get(2));

        //A central se increveu para receber informação de transações
        assertEquals("central/transacoes/+", onSubscribeFutureList.get(3));

        //A cliente se inscreve para receber informações de alarmes e falhas
        assertEquals("central/alarmes_falhas/+", onSubscribeFutureList.get(4));

        //A cliente se inscreve para receber informações de trocas de planos
        assertEquals("central/troca_plano/+", onSubscribeFutureList.get(5));

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
        assertEquals(Long.valueOf(json.get("carimboDeTempo").asLong()), status.getTimestamp());

    }
}

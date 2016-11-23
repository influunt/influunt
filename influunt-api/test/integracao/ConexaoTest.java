package integracao;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import protocol.Envelope;
import status.StatusConexaoControlador;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ConexaoTest extends BasicMQTTTest {


    @Test
    public void centralDeveSeConectarAoServidorMQTT() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        //A central ao se conectar no servidor deve se inscrever em diversos tópicos
        startClient();

        await().until(() -> StatusConexaoControlador.ultimoStatus(idControlador) != null);
        //A central conectou
        assertEquals("central", onConnectFutureList.get(0));

        assertEquals("central", onConnectFutureList.get(0));

        assertEquals("controladores/conn/online", onSubscribeFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica offline
        assertEquals("controladores/conn/offline", onSubscribeFutureList.get(1));

        //A central se increveu para receber informação de central
        assertEquals("central/+", onSubscribeFutureList.get(2));

        //A central se increveu para receber informação de transações
        assertEquals("central/transacoes/+", onSubscribeFutureList.get(3));

        //A cliente se inscreve para receber informações enviadas pela central
        assertEquals("controlador/" + idControlador + "/+", onSubscribeFutureList.get(4));

        //O cliente se conectou
        assertEquals(idControlador, onConnectFutureList.get(1));

        //O cliente envio a CONTROLADOR_ONLINE
        //A central se increveu para receber informação de quando um controlador fica online
        Map map = new Gson().fromJson(new String(onPublishFutureList.get(0)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals("CONTROLADOR_ONLINE", envelope.getTipoMensagem().toString());
        assertEquals("controladores/conn/online",envelope.getDestino());
        assertNotNull(envelope.getCarimboDeTempo());
        assertNotNull(envelope.getCarimboDeTempo());
        assertNotNull(envelope.getIdMensagem());
        assertNotNull(envelope.getConteudo());

        map = new Gson().fromJson(envelope.getConteudo().toString(),Map.class);
        assertTrue(map.containsKey("dataHora"));
        assertTrue(map.containsKey("versao72c"));
        assertTrue(map.containsKey("status"));
        assertEquals("NOVO", map.get("status").toString());


        //Verificar se o registro da conexao foi salvo
        StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(idControlador);
        assertTrue(status.isConectado());
        assertEquals(Long.valueOf(envelope.getCarimboDeTempo()), status.timestamp);

    }
}

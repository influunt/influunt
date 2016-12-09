package integracao;

import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import protocol.Envelope;
import status.StatusConexaoControlador;
import utils.EncryptionUtil;
import utils.GzipUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ConexaoTest extends BasicMQTTTest {


    @Test
    public void centralDeveSeConectarAoServidorMQTT() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        //A central ao se conectar no servidor deve se inscrever em diversos tópicos
        startClient();

        await().until(() -> onSubscribeFutureList.size() > 7);
        //A central conectou
        assertEquals("central", onConnectFutureList.get(0));

        assertEquals("controladores/conn/online", onSubscribeFutureList.get(0));

        //A central se increveu para receber informação de quando um controlador fica offline
        assertEquals("controladores/conn/offline", onSubscribeFutureList.get(1));

        //A central se increveu para receber informação de transações
        assertEquals("central/transacoes/+", onSubscribeFutureList.get(2));

        //A cliente se inscreve para receber informações de alarmes e falhas
        assertEquals("central/alarmes_falhas", onSubscribeFutureList.get(3));

        //A cliente se inscreve para receber informações de trocas de planos
        assertEquals("central/troca_plano", onSubscribeFutureList.get(4));

        //A central se increveu para ler dados do controaldor
        assertEquals("central/configuracao", onSubscribeFutureList.get(5));

        //A central se increveu para ler dados do controaldor
        assertEquals("central/mudanca_status_controlador", onSubscribeFutureList.get(6));

        //A central se increveu para ler dados do controaldor
        assertEquals("central/info", onSubscribeFutureList.get(7));

        //O cliente se conectou
        assertEquals(idControlador, onConnectFutureList.get(1));

        //O cliente envio a CONTROLADOR_ONLINE
        //A central se increveu para receber informação de quando um controlador fica online
        Map map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(0)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getVersaoControlador().getControladorFisico().getCentralPrivateKey()), Envelope.class);

        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals("CONTROLADOR_ONLINE", envelope.getTipoMensagem().toString());
        assertEquals("controladores/conn/online", envelope.getDestino());
        assertNotNull(envelope.getCarimboDeTempo());
        assertNotNull(envelope.getCarimboDeTempo());
        assertNotNull(envelope.getIdMensagem());
        assertNotNull(envelope.getConteudo());

        map = new Gson().fromJson(envelope.getConteudo().toString(), Map.class);
        assertTrue(map.containsKey("dataHora"));
        assertTrue(map.containsKey("versao72c"));
        assertTrue(map.containsKey("status"));
        assertEquals("NOVO", map.get("status").toString());


        //Verificar se o registro da conexao foi salvo
        StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(idControlador);
        assertTrue(status.isConectado());
        assertEquals(Long.valueOf(envelope.getCarimboDeTempo()), status.getTimestamp());

    }
}

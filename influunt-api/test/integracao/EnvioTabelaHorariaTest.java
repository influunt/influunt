package integracao;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import json.ControladorCustomSerializer;
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import protocol.TipoTransacao;
import server.conn.CentralMessageBroker;
import status.Transacao;
import utils.EncryptionUtil;
import utils.TransacaoHelper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.groups.Default;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void enviarPlanosOK() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        controlador = new ControladorHelper().setPlanos(controlador);

        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        enviarPacotePlano();
        assertTransacaoOk();
    }

    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        enviarPacotePlano();
        assertTransacaoErro();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

    private void enviarPacotePlano() {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.enviarPacotePlanos(controlador);
    }

}

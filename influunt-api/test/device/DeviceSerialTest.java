package device;

import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
import engine.GerenciadorDeEstagios;
import engine.GerenciadorDeEstagiosCallback;
import engine.IntervaloGrupoSemaforico;
import integracao.ControladorHelper;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import os72c.client.device.SerialDevice;
import protocol.Mensagem;
import protocol.MensagemEstagio;
import protocol.MensagemInicio;
import protocol.TipoDeMensagemBaixoNivel;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static sun.misc.PostVMInitHook.run;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class DeviceSerialTest {


    @Test
    public void testConnectividade(){
        SerialDevice serialDevice = new SerialDevice();
        serialDevice.sendMensagem(TipoDeMensagemBaixoNivel.INICIO);

    }

}

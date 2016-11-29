package device;

import config.WithInfluuntApplicationNoAuthentication;
import config.WithLocalInfluuntApplicationNoAuthentication;
import integracao.BasicMQTTTest;
import integracao.ControladorHelper;
import models.Controlador;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import os72c.client.Client;
import os72c.client.conf.DeviceConfig;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class DeviceClientTest extends BasicMQTTTest {

    @Test
    @Ignore
    public void execucao() throws InterruptedException {
        controlador = new ControladorHelper().setPlanos(controlador);
        Client client = provideApp.injector().instanceOf(Client.class);
        Thread.sleep(600000l);

    }
}

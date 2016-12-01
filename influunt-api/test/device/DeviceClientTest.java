package device;

import integracao.BasicMQTTTest;
import integracao.ControladorHelper;
import org.junit.Ignore;
import org.junit.Test;
import os72c.client.Client;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class DeviceClientTest extends BasicMQTTTest {

    @Test
    @Ignore
    public void execucao() throws InterruptedException {
        controlador = new ControladorHelper().setPlanos(controlador);

        Client client = new Client(this.deviceConfig);
        Thread.sleep(600000000L);
    }
}

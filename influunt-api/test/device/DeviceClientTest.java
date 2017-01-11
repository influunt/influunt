package device;

import integracao.BasicMQTTTest;
import integracao.ControladorHelper;
import org.junit.Ignore;
import org.junit.Test;
import os72c.client.Client;
import os72c.client.virtual.ControladorForm;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class DeviceClientTest extends BasicMQTTTest {

    @Test
    @Ignore
    public void execucao() throws InterruptedException {
        controlador = new ControladorHelper().setPlanos(controlador);
        this.deviceConfig.setDeviceBridge(new ControladorForm());
        new Client(this.deviceConfig);
        Thread.sleep(600000000L);
    }
}

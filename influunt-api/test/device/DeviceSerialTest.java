package device;

import org.junit.Ignore;
import org.junit.Test;
import os72c.client.device.SerialDevice;
import protocol.TipoDeMensagemBaixoNivel;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class DeviceSerialTest {

    @Test
    @Ignore
    public void testConnectividade() {
        SerialDevice serialDevice = new SerialDevice();
        serialDevice.sendMensagem(TipoDeMensagemBaixoNivel.INICIO);
    }

}

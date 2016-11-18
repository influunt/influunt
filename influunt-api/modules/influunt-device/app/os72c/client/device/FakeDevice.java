package os72c.client.device;

import engine.IntervaloGrupoSemaforico;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.commons.lang.ArrayUtils;
import os72c.client.exceptions.HardwareFailureException;
import protocol.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by rodrigosol on 11/4/16.
 */
public class FakeDevice implements DeviceBridge {

    @Override
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico) {

    }

    @Override
    public void start(DeviceBridgeCallback callback) {

    }
}

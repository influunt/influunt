package os72c.client.conf;

import os72c.client.device.DeviceBridge;
import os72c.client.device.FakeDevice;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class LocalDeviceConfig implements DeviceConfig {
    private String host = "mosquitto.rarolabs.com.br";
    private String controladorId = "6e864974-7b0a-435e-a8fb-f73beb33e187";
    private String port = "1883";
    private String privateKey;
    private String publicKey;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getDeviceId() {
        return controladorId;
    }

    @Override
    public void setDeviceId(String id) {
        this.controladorId = id;
    }

    @Override
    public String getCentralPublicKey() {
        return publicKey;
    }

    @Override
    public void setCentralPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public DeviceBridge getDeviceBridge() {
        return new FakeDevice();
    }
}

package os72c.client.conf;


import com.google.inject.Singleton;
import os72c.client.device.DeviceBridge;
import os72c.client.device.FakeDevice;

@Singleton
public class TestDeviceConfig implements DeviceConfig {
    private String id;

    private String centralPublicKey;

    private String privateKey;

    @Override
    public String getHost() {
        return "127.0.0.1";
    }

    @Override
    public String getPort() {
        return "1883";
    }

    @Override
    public String getDeviceId() {
        return id;
    }

    @Override
    public void setDeviceId(String id) {
        this.id = id;
    }

    @Override
    public String getCentralPublicKey() {
        return centralPublicKey;
    }

    @Override
    public void setCentralPublicKey(String centralPublicKey) {
        this.centralPublicKey = centralPublicKey;
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

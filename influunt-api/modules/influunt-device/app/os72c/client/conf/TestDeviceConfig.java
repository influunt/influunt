package os72c.client.conf;


import com.google.inject.Singleton;
import os72c.client.device.DeviceBridge;
import os72c.client.device.FakeDevice;

@Singleton
public class TestDeviceConfig implements DeviceConfig {
    private String id;

    private String centralPublicKey;

    private String privateKey;

    private String host = "127.0.0.1";

    private String port = "1883";

    private DeviceBridge bridge;

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
        return this.bridge != null ? this.bridge : new FakeDevice();
    }

    @Override
    public void setDeviceBridge(DeviceBridge deviceBridge) {
        this.bridge = deviceBridge;
    }

}

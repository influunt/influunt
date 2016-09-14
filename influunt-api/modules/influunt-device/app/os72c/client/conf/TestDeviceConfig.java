package os72c.client.conf;


import com.google.inject.Singleton;

@Singleton
public class TestDeviceConfig implements DeviceConfig {
    private String id;

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
}

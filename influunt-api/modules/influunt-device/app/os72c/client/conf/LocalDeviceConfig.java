package os72c.client.conf;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class LocalDeviceConfig implements DeviceConfig {
    @Override
    public String getHost() {
        return "mosquitto.rarolabs.com.br";
    }

    @Override
    public String getPort() {
        return "1883";
    }

    @Override
    public String getDeviceId() {
        return "6e864974-7b0a-435e-a8fb-f73beb33e187";
    }

    @Override
    public void setDeviceId(String id) {

    }
}

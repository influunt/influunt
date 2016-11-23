package os72c.client.conf;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class LocalDeviceConfig implements DeviceConfig {
    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getPort() {
        return "1883";
    }

    @Override
    public String getDeviceId() {
        return "a984b485-f100-4537-ada0-211deffd52c5";
    }

    @Override
    public void setDeviceId(String id) {

    }

    @Override
    public String getCentralPublicKey() {
        return null;
    }

    @Override
    public void setCentralPublicKey(String publicKey) {

    }

    @Override
    public String getPrivateKey() {
        return null;
    }

    @Override
    public void setPrivateKey(String publicKey) {

    }
}

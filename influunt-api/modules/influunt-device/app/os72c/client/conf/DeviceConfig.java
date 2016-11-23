package os72c.client.conf;

/**
 * Created by leonardo on 9/13/16.
 */
public interface DeviceConfig {
    public String getHost();

    public String getPort();

    public String getDeviceId();

    public void setDeviceId(String id);

    public void setCentralPublicKey(String publicKey);
    public String getCentralPublicKey();

    public void setPrivateKey(String publicKey);
    public String getPrivateKey();

}

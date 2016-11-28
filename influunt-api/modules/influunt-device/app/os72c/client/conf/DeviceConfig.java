package os72c.client.conf;

/**
 * Created by leonardo on 9/13/16.
 */
public interface DeviceConfig {
    public String getHost();
    public void setHost(String host);

    public String getPort();
    public void setPort(String port);

    public String getDeviceId();

    public void setDeviceId(String id);

    public String getCentralPublicKey();

    public void setCentralPublicKey(String publicKey);

    public String getPrivateKey();

    public void setPrivateKey(String publicKey);

}

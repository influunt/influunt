package os72c.client.conf;

/**
 * Created by leonardo on 9/13/16.
 */
public interface DeviceConfig {
    public String getHost();

    public String getPort();

    public String getDeviceId();

    public void setDeviceId(String id);
}

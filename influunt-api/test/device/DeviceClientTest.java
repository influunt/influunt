package device;

import config.WithLocalInfluuntApplicationNoAuthentication;
import org.junit.Ignore;
import org.junit.Test;
import os72c.client.Client;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class DeviceClientTest extends WithLocalInfluuntApplicationNoAuthentication {

    @Test
    @Ignore
    public void execucao() throws InterruptedException {

        Client client = provideApp.injector().instanceOf(Client.class);
        Thread.sleep(600000l);

    }
}

package device;

import config.WithInfluuntApplicationNoAuthentication;
import org.junit.Ignore;
import org.junit.Test;
import os72c.client.Client;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class DeviceClientTest extends WithInfluuntApplicationNoAuthentication{

    @Test
    @Ignore
    public void execucao() throws InterruptedException {

        Client client = provideApp.injector().instanceOf(Client.class);
        Thread.sleep(600000l);

    }
}

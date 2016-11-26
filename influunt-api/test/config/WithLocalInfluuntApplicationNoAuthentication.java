package config;


import be.objectify.deadbolt.java.cache.HandlerCache;
import com.google.inject.Singleton;
import config.auth.TestDeadboltHandlerCache;
import os72c.client.conf.DeviceConfig;
import os72c.client.conf.LocalDeviceConfig;
import os72c.client.device.DeviceBridge;
import os72c.client.device.SerialDevice;
import os72c.client.storage.MapStorage;
import os72c.client.storage.Storage;
import os72c.client.storage.StorageConf;
import os72c.client.storage.TestStorageConf;
import os72c.client.virtual.ControladorForm;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.Map;

import static play.inject.Bindings.bind;

/**
 * Created by pedropires on 9/8/16.
 */
public class WithLocalInfluuntApplicationNoAuthentication extends WithInfluuntApplication {

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Application createApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
            .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
            .overrides(bind(HandlerCache.class).to(TestDeadboltHandlerCache.class).in(Singleton.class))
            .overrides(bind(DeviceConfig.class).to(LocalDeviceConfig.class).in(Singleton.class))
            .overrides(bind(DeviceBridge.class).to(ControladorForm.class).in(Singleton.class))
            .overrides(bind(StorageConf.class).to(TestStorageConf.class).in(Singleton.class))
            .overrides(bind(Storage.class).to(MapStorage.class).in(Singleton.class))
            .in(Mode.TEST).build();
    }

}

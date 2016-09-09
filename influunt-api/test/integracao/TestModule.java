package integracao;

import com.google.inject.AbstractModule;

/**
 * Created by rodrigosol on 9/8/16.
 */
public class TestModule extends AbstractModule {
    @Override
    public void configure() {
        bind(server.Central.class).asEagerSingleton();
    }
}

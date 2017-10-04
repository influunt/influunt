package test.config.auth;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;
import com.google.inject.Singleton;
import security.HandlerKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pedropires on 9/13/16.
 */
@Singleton
public class TestDeadboltHandlerCache implements HandlerCache {

    private final Map<String, DeadboltHandler> handlers = new HashMap<>();

    public TestDeadboltHandlerCache() {
        handlers.put(HandlerKeys.DEFAULT.key, new TestDeadboltHandler());
    }

    @Override
    public DeadboltHandler apply(final String key) {
        return handlers.get(key);
    }

    @Override
    public DeadboltHandler get() {
        return handlers.get(HandlerKeys.DEFAULT.key);
    }

}

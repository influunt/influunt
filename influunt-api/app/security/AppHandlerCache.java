package security;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;

import be.objectify.deadbolt.java.DeadboltHandler;

@Singleton
public class AppHandlerCache implements be.objectify.deadbolt.java.cache.HandlerCache {

	private final Map<String, DeadboltHandler> handlers = new HashMap<>();
	
	public AppHandlerCache() {
		handlers.put(HandlerKeys.DEFAULT.key, new AppDeadboltHandler());
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

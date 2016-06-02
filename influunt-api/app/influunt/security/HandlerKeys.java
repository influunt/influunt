package influunt.security;

import be.objectify.deadbolt.java.ConfigKeys;

public enum HandlerKeys
{
    DEFAULT(ConfigKeys.DEFAULT_HANDLER_KEY),
    ALT("altHandler"),
    BUGGY("buggyHandler"),
    NO_USER("noUserHandler");

    public final String key;

    private HandlerKeys(final String key)
    {
        this.key = key;
    }
}
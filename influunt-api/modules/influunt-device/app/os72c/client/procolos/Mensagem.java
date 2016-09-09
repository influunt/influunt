package os72c.client.procolos;

import java.util.UUID;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class Mensagem {

    public final String id = UUID.randomUUID().toString();

    public final Long timestamp = System.currentTimeMillis();

}

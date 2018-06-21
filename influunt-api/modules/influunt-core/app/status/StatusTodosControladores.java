package status;

import java.io.IOException;
import java.util.Map;

public class StatusTodosControladores {

    private  Map<String, Map<String, Float>> status;

    public static Map<String, Map<String, Float>> getStatusTodosControladores(StatusAtualControlador statusAtualControlador) throws IOException {

        StatusTodosControladores statusTodosControladoresObj = statusAtualControlador.findOne("{}").as(StatusTodosControladores.class);
        if(statusTodosControladoresObj != null)
            return statusTodosControladoresObj.status;

        return null;
    }
}

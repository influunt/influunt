package protocol;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Sinal {


    private Sinal() {
    }

    public static Envelope getMensagem(TipoMensagem tipoMensagem, String idControlador, String destino,String publicKey) {
        if(publicKey!=null){
            ObjectNode root = Json.newObject();
            root.put("publicKey",publicKey);
            return new Envelope(tipoMensagem, idControlador, destino, 1, root.toString(), null);
        }else{
            return new Envelope(tipoMensagem, idControlador, destino, 1, null, null);
        }

    }

}

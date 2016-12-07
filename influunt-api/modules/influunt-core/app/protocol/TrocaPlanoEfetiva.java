package protocol;

import engine.AgendamentoTrocaPlano;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TrocaPlanoEfetiva {

    public static Envelope getMensagem(String idControlador, AgendamentoTrocaPlano agendamentoTrocaPlano) {
        return new Envelope(TipoMensagem.TROCA_DE_PLANO,
            idControlador,
            DestinoCentral.trocaDePlanoEfetiva(),
            2,
            Json.toJson(agendamentoTrocaPlano).toString(),
            null);
    }
}

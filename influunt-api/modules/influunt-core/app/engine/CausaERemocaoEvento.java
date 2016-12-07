package engine;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.M;

/**
 * Created by rodrigosol on 12/7/16.
 */
public class CausaERemocaoEvento {


    private static Map<TipoEvento, TipoEvento> causaRemocao = new HashMap<>();

    static {
        causaRemocao.put(TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE, TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO);
        causaRemocao.put(TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE, TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO);

        causaRemocao.put(TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR, TipoEvento.FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO);
        causaRemocao.put(TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR, TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO);

        causaRemocao.put(TipoEvento.REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO, TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA);

        causaRemocao.put(TipoEvento.REMOCAO_FALHA_VERDES_CONFLITANTES, TipoEvento.FALHA_VERDES_CONFLITANTES);
    }

    public static TipoEvento getFalha(TipoEvento remocao) {
        return causaRemocao.get(remocao);
    }
}

package os72c.client.models;

/**
 * Created by rodrigosol on 7/13/16.
 */
public enum TipoInterrupcao {

    DETECTOR_PEDESTRE("P"),
    DETECTOR_VEICULAR("V"),
    ERRO("E"),
    OPERACAO_MANUAL("M"),
    OPERACAO_NORMAL("N");

    private final String tipo;

    private TipoInterrupcao(String tipo) {
        this.tipo = tipo;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : tipo.equals(otherName);
    }

    public static TipoInterrupcao fromString(String text) {
        if (text != null) {
            for (TipoInterrupcao b : TipoInterrupcao.values()) {
                if (text.equalsIgnoreCase(b.tipo)) {
                    return b;
                }
            }
        }
        return null;
    }

}

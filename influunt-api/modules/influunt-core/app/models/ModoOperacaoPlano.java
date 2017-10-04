package models;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
public enum ModoOperacaoPlano {

    TEMPO_FIXO_ISOLADO("Isolado"),
    TEMPO_FIXO_COORDENADO("Coordenado"),
    ATUADO("Atuado"),
    APAGADO("Apagado"),
    INTERMITENTE("Intermitente"),
    MANUAL("Manual");

    private String name;

    ModoOperacaoPlano(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

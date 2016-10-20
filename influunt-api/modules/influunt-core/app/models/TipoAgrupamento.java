package models;

/**
 * Created by pedropires on 7/7/16.
 */
public enum TipoAgrupamento {

    ROTA("Rota"), CORREDOR("Corredor");

    private String name;

    TipoAgrupamento(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && otherName.equals(name);
    }

}

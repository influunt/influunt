package models;

/**
 * Created by pedropires on 7/7/16.
 */
public enum TipoAgrupamento {
    SUBAREA("Subárea"), ROTA("Rota"), CORREDOR("Corredor");

    private String name;

    TipoAgrupamento(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    @Override
    public String toString() {
        return name;
    }
}

package models;

import com.avaje.ebean.annotation.EnumValue;

public enum TipoTransicao {

    @EnumValue("GANHO_DE_PASSAGEM")
    GANHO_DE_PASSAGEM,

    @EnumValue("PERDA_DE_PASSAGEM")
    PERDA_DE_PASSAGEM;
}

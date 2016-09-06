package models;

import com.avaje.ebean.annotation.EnumValue;

/**
 * Created by rodrigosol on 6/17/16.
 */
public enum TipoGrupoSemaforico {
    @EnumValue("VEICULAR")
    VEICULAR,
    @EnumValue("PEDESTRE")
    PEDESTRE;
}

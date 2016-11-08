package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;


/**
 * Created by rodrigosol on 10/4/16.
 */
public class ParametroSimulacaoManual {

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime disparo;

    private boolean ativar;

    public ParametroSimulacaoManual(DateTime disparo, boolean ativar) {
        this.disparo = disparo;
        this.ativar = ativar;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        if (ativar) {
            return new EventoMotor(disparo, TipoEvento.INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL);
        } else {
            return new EventoMotor(disparo, TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL);
        }
    }

    public boolean isAtivar() {
        return ativar;
    }

    public void setAtivar(boolean ativar) {
        this.ativar = ativar;
    }
}

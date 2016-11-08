package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoImposicaoPlanoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoImposicaoPlanoDeserializer.class)
public class ParametroSimulacaoImposicaoPlano {

    @NotNull(message = "não pode ficar em branco")
    private Integer plano;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    public Integer getPlano() {
        return plano;
    }

    public void setPlano(Integer plano) {
        this.plano = plano;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        return new EventoMotor(disparo, TipoEvento.IMPOSICAO_PLANO, plano);
    }

}

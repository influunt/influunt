package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoImposicaoModoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoImposicaoModoDeserializer.class)
public class ParametroSimulacaoImposicaoModo {

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    @NotNull(message = "não pode ficar em branco")
    private Integer duracao;

    @NotNull(message = "não pode ficar em branco")
    private String modoOperacao;

    @NotNull(message = "não pode ficar em branco")
    private Integer anel;

    public String getModoOperacao() {
        return modoOperacao;
    }

    public void setModoOperacao(String modoOperacao) {
        this.modoOperacao = modoOperacao;
    }

    public Integer getAnel() {
        return anel;
    }

    public void setAnel(Integer anel) {
        this.anel = anel;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public EventoMotor toEvento() {
        return new EventoMotor(disparo.minus(200), TipoEvento.IMPOSICAO_MODO, modoOperacao, anel, duracao, disparo.getMillis());
    }
}

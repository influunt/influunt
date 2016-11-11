package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoFalhaDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Anel;
import models.Detector;
import models.GrupoSemaforico;
import org.joda.time.DateTime;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoFalhaDeserializer.class)
public class ParametroSimulacaoFalha {

    @NotNull(message = "não pode ficar em branco")
    private TipoEvento falha;

    private GrupoSemaforico grupoSemaforico;

    private Detector detector;

    private Anel anel;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    public EventoMotor toEvento() {
        Object params = null;
        if (grupoSemaforico != null) {
            params = grupoSemaforico;
        } else if (anel != null) {
            params = anel.getPosicao();
        } else if (detector != null) {
            params = detector;
        }
        return new EventoMotor(getDisparo(), getFalha(), params);
    }

    public TipoEvento getFalha() {
        return falha;
    }

    public void setFalha(TipoEvento falha) {
        this.falha = falha;
    }

    public GrupoSemaforico getGrupoSemaforico() {
        return grupoSemaforico;
    }

    public Detector getDetector() {
        return detector;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setParametro(String id) {
        switch (TipoEvento.getFalha(falha.getCodigo())) {
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO:
            case FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO:
                grupoSemaforico = GrupoSemaforico.find.byId(UUID.fromString(id));
                break;
            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                detector = Detector.find.byId(UUID.fromString(id));
                break;
            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
            case FALHA_VERDES_CONFLITANTES:
            case FALHA_VERDES_CONFLITANTES_REMOCAO:
                anel = Anel.find.byId(UUID.fromString(id));
                break;
        }
    }

    @AssertTrue(message = "deve ter pelo menos um dos parâmetros.")
    public boolean deveTerPeloMenosUmParametro() {
        return detector != null || grupoSemaforico != null || anel != null;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }
}

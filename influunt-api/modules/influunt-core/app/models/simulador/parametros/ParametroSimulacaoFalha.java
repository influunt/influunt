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

import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoFalhaDeserializer.class)
public class ParametroSimulacaoFalha {

    private TipoEvento falha;

    private GrupoSemaforico grupoSemaforico;

    private Detector detector;

    private Anel anel;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime disparo;

    public EventoMotor toEvento() {
        return null;
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

//    public void setGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
//        this.grupoSemaforico = grupoSemaforico;
//    }

    public Detector getDetector() {
        return detector;
    }

//    public void setDetector(Detector detector) {
//        this.detector = detector;
//    }

    public Anel getAnel() {
        return anel;
    }

//    public void setAnel(Anel anel) {
//        this.anel = anel;
//    }

    public void setParametro(String id) {
        switch(TipoEvento.getFalha(falha.getCodigo())) {
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
                anel = Anel.find.byId(UUID.fromString(id));
                break;
        }
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }
}

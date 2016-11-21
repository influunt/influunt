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
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
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

    private Pair<Integer, TipoDetector> detector;

    private Anel anel;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    public EventoMotor toEvento() {
        if (grupoSemaforico != null) {
            return new EventoMotor(getDisparo(), getFalha(), grupoSemaforico, anel.getPosicao());
        } else if (detector != null) {
            return new EventoMotor(getDisparo(), getFalha(), detector, anel.getPosicao());
        } else if (anel != null) {
            return new EventoMotor(getDisparo(), getFalha(), anel.getPosicao());
        } else {
            return new EventoMotor(getDisparo(), getFalha());
        }
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

    public Pair<Integer, TipoDetector> getDetector() {
        return detector;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setParametro(String id) {
        switch (TipoEvento.getFalha(falha.getCodigo())) {
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO:
            case ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA:
            case ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO:
                grupoSemaforico = GrupoSemaforico.find.byId(UUID.fromString(id));
                this.anel = grupoSemaforico.getAnel();
                break;
            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                Detector detector = Detector.find.byId(UUID.fromString(id));
                this.detector = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
                this.anel = detector.getAnel();
                break;
            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
            case FALHA_VERDES_CONFLITANTES:
            case REMOCAO_FALHA_VERDES_CONFLITANTES:
            case FALHA_SEQUENCIA_DE_CORES:
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

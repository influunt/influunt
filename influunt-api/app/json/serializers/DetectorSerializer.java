package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.Detector;
import models.Estagio;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/11/16.
 */
public class DetectorSerializer extends JsonSerializer<Detector> {

    @Override
    public void serialize(Detector detector, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        if (detector.getId() != null) {
            jgen.writeStringField("id", detector.getId().toString());
        }
        if (detector.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(detector.getDataCriacao()));
        }
        if (detector.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(detector.getDataAtualizacao()));
        }
        if (detector.getTipo() != null) {
            jgen.writeStringField("tipo", detector.getTipo().toString());
        }
        if (detector.getPosicao() != null) {
            jgen.writeNumberField("posicao", detector.getPosicao());
        }
        if (detector.getDescricao() != null) {
            jgen.writeStringField("descricao", detector.getDescricao());
        }
        if (detector.getMonitorado() != null) {
            jgen.writeBooleanField("monitorado", detector.getMonitorado());
        }
        if (detector.getTempoAusenciaDeteccaoMinima() != null) {
            jgen.writeNumberField("tempoAusenciaDeteccaoMinima", detector.getTempoAusenciaDeteccaoMinima());
        }
        if (detector.getTempoAusenciaDeteccaoMaxima() != null) {
            jgen.writeNumberField("tempoAusenciaDeteccaoMaxima", detector.getTempoAusenciaDeteccaoMaxima());
        }
        if (detector.getTempoDeteccaoPermanenteMinima() != null) {
            jgen.writeNumberField("tempoDeteccaoPermanenteMinima", detector.getTempoDeteccaoPermanenteMinima());
        }
        if (detector.getTempoDeteccaoPermanenteMaxima() != null) {
            jgen.writeNumberField("tempoDeteccaoPermanenteMaxima", detector.getTempoDeteccaoPermanenteMaxima());
        }
        if (detector.getAnel() != null) {
            Anel anel = ObjectUtils.clone(detector.getAnel());
            anel.setDetectores(null);
            anel.setGruposSemaforicos(null);
            anel.setControlador(null);
            anel.setEstagios(null);
            anel.setPlanos(null);
            jgen.writeObjectField("anel", anel);
        }
        if (detector.getEstagio() != null) {
            Estagio estagio = ObjectUtils.clone(detector.getEstagio());
            estagio.setDetector(null);
            estagio.setEstagiosGruposSemaforicos(null);
            estagio.setOrigemDeTransicoesProibidas(null);
            estagio.setDestinoDeTransicoesProibidas(null);
            estagio.setAlternativaDeTransicoesProibidas(null);
            estagio.setAnel(null);
            jgen.writeObjectField("estagio", estagio);
        }

        jgen.writeEndObject();

    }
}

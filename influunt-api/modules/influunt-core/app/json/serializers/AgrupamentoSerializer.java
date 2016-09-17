package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Agrupamento;
import models.Controlador;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AgrupamentoSerializer extends JsonSerializer<Agrupamento> {

    @Override
    public void serialize(Agrupamento agrupamento, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (agrupamento.getId() == null) {
            jgen.writeStringField("id", null);
        } else {
            jgen.writeStringField("id", agrupamento.getId().toString());
        }
        if (agrupamento.getNome() != null) {
            jgen.writeStringField("nome", agrupamento.getNome().toString());
        }
        if (agrupamento.getNumero() != null) {
            jgen.writeStringField("numero", agrupamento.getNumero().toString());
        }
        if (agrupamento.getTipo() != null) {
            jgen.writeObjectField("tipo", agrupamento.getTipo());
        }
        if (agrupamento.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(agrupamento.getDataCriacao()));
        }
        if (agrupamento.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(agrupamento.getDataAtualizacao()));
        }
        if (agrupamento.getControladores() != null) {
            jgen.writeArrayFieldStart("controladores");
            for (Controlador controlador : agrupamento.getControladores()) {
                Controlador controladorAux = ObjectUtils.clone(controlador);
                controladorAux.setAneis(null);
                controladorAux.setModelo(null);
                controladorAux.setArea(null);
                controladorAux.setDetectores(null);
                controladorAux.setGruposSemaforicos(null);
                controladorAux.setAgrupamentos(null);
                controladorAux.setVersoesTabelasHorarias(null);
                jgen.writeObject(controladorAux);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}

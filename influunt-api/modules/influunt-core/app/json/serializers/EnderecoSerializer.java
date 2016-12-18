package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Controlador;
import models.Endereco;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/29/16.
 */
public class EnderecoSerializer extends JsonSerializer<Endereco> {


    @Override
    public void serialize(Endereco endereco, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (endereco.getId() != null) {
            jgen.writeStringField("id", endereco.getId().toString());
        }
        if (endereco.getLocalizacao() != null) {
            jgen.writeStringField("localizacao", endereco.getLocalizacao());
        }
        if (endereco.getLocalizacao2() != null) {
            jgen.writeStringField("localizacao2", endereco.getLocalizacao2());
        }
        if (endereco.getReferencia() != null) {
            jgen.writeStringField("referencia", endereco.getReferencia());
        }
        if (endereco.getAlturaNumerica() != null) {
            jgen.writeNumberField("alturaNumerica", endereco.getAlturaNumerica());
        }
        if (endereco.getLatitude() != null) {
            jgen.writeNumberField("latitude", endereco.getLatitude());
        }
        if (endereco.getLongitude() != null) {
            jgen.writeNumberField("longitude", endereco.getLongitude());
        }
        if (endereco.getControlador() != null) {
            Controlador controlador = ObjectUtils.clone(endereco.getControlador());
            controlador.setAneis(null);
            controlador.setEndereco(null);
            controlador.setGruposSemaforicos(null);
            controlador.setVersoesTabelasHorarias(null);
            controlador.getVersaoControlador().setControlador(null);
            jgen.writeObjectField("controlador", controlador);
        }
        if (endereco.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(endereco.getDataCriacao()));
        }
        if (endereco.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(endereco.getDataAtualizacao()));
        }
        jgen.writeEndObject();
    }
}

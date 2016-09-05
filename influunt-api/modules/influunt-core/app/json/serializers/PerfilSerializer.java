package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Perfil;
import models.Permissao;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class PerfilSerializer extends JsonSerializer<Perfil> {


    @Override
    public void serialize(Perfil perfil, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (perfil.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", perfil.getId().toString());
        }
        if (perfil.getNome() != null) {
            jgen.writeStringField("nome", perfil.getNome());
        }
        if (perfil.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(perfil.getDataCriacao()));
        }
        if (perfil.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(perfil.getDataAtualizacao()));
        }
        jgen.writeArrayFieldStart("permissoes");
        for (Permissao permissao : perfil.getPermissoes()) {
            jgen.writeStartObject();
            jgen.writeStringField("id", permissao.getId().toString());
            jgen.writeStringField("descricao", permissao.getDescricao());
            jgen.writeStringField("chave", permissao.getChave());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}

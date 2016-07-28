package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Usuario;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class UsuarioSerializer extends JsonSerializer<Usuario> {


    @Override
    public void serialize(Usuario usuario, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (usuario.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", usuario.getId().toString());
        }
        if (usuario.getLogin() == null) {
            jgen.writeNullField("login");
        } else {
            jgen.writeStringField("login", usuario.getLogin());
        }
        if (usuario.getEmail() != null) {
            jgen.writeStringField("email", usuario.getEmail());
        }

        if (usuario.getNome() != null) {
            jgen.writeStringField("nome", usuario.getNome());
        }
        if (usuario.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(usuario.getDataCriacao()));
        }
        if (usuario.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(usuario.getDataAtualizacao()));
        }
        if (usuario.getArea() != null) {
            jgen.writeObjectField("area", usuario.getArea());
        }
        if (usuario.getPerfil() != null) {
            jgen.writeObjectField("perfil", usuario.getPerfil());
        }

        jgen.writeEndObject();
    }
}

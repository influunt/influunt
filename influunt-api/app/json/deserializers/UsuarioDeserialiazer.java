package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.Perfil;
import models.Usuario;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/25/16.
 */
public class UsuarioDeserialiazer extends JsonDeserializer<Usuario> {

    @Override
    public Usuario deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Usuario usuario = new Usuario();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                usuario.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("login")) {
            usuario.setLogin(node.get("login").asText());
        }

        if (node.has("email")) {
            usuario.setEmail(node.get("email").asText());
        }

        if (node.has("nome")) {
            usuario.setNome(node.get("nome").asText());
        }

        if (node.has("area")) {
            usuario.setArea(Json.fromJson(node.get("area"), Area.class));
        }

        if (node.has("perfil")) {
            usuario.setPerfil(Json.fromJson(node.get("perfil"), Perfil.class));
        }

        return usuario;
    }
}

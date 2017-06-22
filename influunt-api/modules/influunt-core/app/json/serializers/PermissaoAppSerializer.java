package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Permissao;
import models.PermissaoApp;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class PermissaoAppSerializer extends JsonSerializer<PermissaoApp> {

    @Override
    public void serialize(PermissaoApp permissaoApp, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (permissaoApp.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", permissaoApp.getId().toString());
        }

        if (permissaoApp.getNome() != null) {
            jgen.writeStringField("nome", permissaoApp.getNome());
        }

        if (permissaoApp.getDescricao() != null) {
            jgen.writeStringField("descricao", permissaoApp.getDescricao());
        }

        if (permissaoApp.getChave() != null) {
            jgen.writeStringField("chave", permissaoApp.getChave());
        }

        if (permissaoApp.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(permissaoApp.getDataCriacao()));
        }

        if (permissaoApp.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(permissaoApp.getDataAtualizacao()));
        }

        if (permissaoApp.getPermissoes() != null) {
            jgen.writeArrayFieldStart("permissoes");
            for (Permissao permissao : permissaoApp.getPermissoes()) {
                jgen.writeStartObject();
                jgen.writeStringField("id", permissao.getId().toString());
                jgen.writeStringField("chave", permissao.getChave());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}

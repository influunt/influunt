package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ErroSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object erro, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        throw new RuntimeException(erro.getClass().toString());
    }
}

package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import engine.AgendamentoTrocaPlano;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AgendamentoTrocaPlanoSerializer extends JsonSerializer<AgendamentoTrocaPlano> {

    @Override
    public void serialize(AgendamentoTrocaPlano agendamentoTrocaPlano, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();


        if (agendamentoTrocaPlano.getMomentoDaTroca() != null) {
            jgen.writeStringField("momentoDaTroca", InfluuntDateTimeSerializer.parse(agendamentoTrocaPlano.getMomentoDaTroca()));
        }

        if (agendamentoTrocaPlano.getMomentoOriginal() != null) {
            jgen.writeStringField("momentoOriginal", InfluuntDateTimeSerializer.parse(agendamentoTrocaPlano.getMomentoOriginal()));
        }

        if (agendamentoTrocaPlano.getAnel() != null) {
            jgen.writeObjectFieldStart("anel");
            jgen.writeStringField("posicao", agendamentoTrocaPlano.getAnel().toString());
            jgen.writeEndObject();
        }


        jgen.writeBooleanField("impostoPorFalha", agendamentoTrocaPlano.isImpostoPorFalha());
        jgen.writeBooleanField("imposicaoDePlano", agendamentoTrocaPlano.isImposicaoPlano());


        if (agendamentoTrocaPlano.getPlano() != null) {
            jgen.writeObjectFieldStart("plano");
            jgen.writeStringField("posicao", agendamentoTrocaPlano.getPlano().getPosicao().toString());
            jgen.writeStringField("modoOperacao", agendamentoTrocaPlano.getPlano().getModoOperacao().toString());
            jgen.writeStringField("descricao", agendamentoTrocaPlano.getPlano().getDescricao());
            jgen.writeEndObject();
        }


        jgen.writeEndObject();
    }
}

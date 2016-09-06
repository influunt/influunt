package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat;
import com.fasterxml.jackson.datatype.joda.ser.JodaDateSerializerBase;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 6/16/16.
 */
public class InfluuntDateTimeSerializer extends JodaDateSerializerBase<DateTime> {

    private static final long serialVersionUID = -1748376807878305485L;

    private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

    public InfluuntDateTimeSerializer() {
        this(new JacksonJodaDateFormat(DATETIME_FORMAT));
    }

    public InfluuntDateTimeSerializer(JacksonJodaDateFormat format) {
        // false -> no arrays (numbers)
        super(DateTime.class, format, false,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static String parse(DateTime dateTime) {
        return DATETIME_FORMAT.print(dateTime);
    }

    @Override
    public InfluuntDateTimeSerializer withFormat(JacksonJodaDateFormat formatter) {
        return (_format == formatter) ? this : new InfluuntDateTimeSerializer(formatter);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, DateTime value) {
        return (value.getMillis() == 0L);
    }

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(DATETIME_FORMAT.print(value));
    }

}

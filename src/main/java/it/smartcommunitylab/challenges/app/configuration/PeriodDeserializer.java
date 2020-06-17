package it.smartcommunitylab.challenges.app.configuration;

import java.io.IOException;
import java.time.Period;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class PeriodDeserializer extends StdDeserializer<Period> {

    public PeriodDeserializer() {
        super(Period.class);
    }

    private static final long serialVersionUID = -7014922798969118756L;

    @Override
    public Period deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        final String periodAsIsoFormat = "P" + jsonParser.getText().toUpperCase();
        return Period.parse(periodAsIsoFormat);
    }

}

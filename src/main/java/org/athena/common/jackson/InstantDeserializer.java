package org.athena.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    public static final InstantDeserializer INSTANCE = new InstantDeserializer();

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        if (jsonParser != null && StringUtils.isNotEmpty(jsonParser.getText())) {
            return Instant.parse(jsonParser.getText());
        } else {
            return null;
        }

    }

}

package org.athena.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

public class InstantSerializer extends JsonSerializer<Instant> {

    public static final InstantSerializer instance = new InstantSerializer();

    @Override
    public void serialize(Instant instant, JsonGenerator generator, SerializerProvider serializers) throws IOException {

        generator.writeString(instant.toString());

    }

}

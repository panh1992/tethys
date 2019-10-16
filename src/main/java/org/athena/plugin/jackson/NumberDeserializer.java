package org.athena.plugin.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberDeserializer extends JsonDeserializer<Number> {

    public static final NumberDeserializer INSTANCE = new NumberDeserializer();

    @Override
    public Number deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        if (jsonParser != null && !Strings.isNullOrEmpty(jsonParser.getText())) {
            return Double.parseDouble(jsonParser.getText());
        }
        return null;

    }

}

package com.omer.ticketing.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfiguration {
    @Bean
    SimpleModule jacksonDeserializerConf() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(String.class, new StdDeserializer<String>(String.class) {
            @Override
            public String deserialize(JsonParser parser, DeserializationContext context)
                    throws IOException {
                String result = StringDeserializer.instance.deserialize(parser, context).trim();
                if (StringUtils.isEmpty(result)) {
                    return null;
                }
                return result;
            }
        });
        module.addDeserializer(LocalDateTime.class, new StdDeserializer<LocalDateTime>(LocalDateTime.class) {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
                    throws IOException {
                return LocalDateTime.parse(parser.readValueAs(String.class), DateTimeFormatter.ISO_DATE_TIME);
            }
        });
        return module;
    }
}

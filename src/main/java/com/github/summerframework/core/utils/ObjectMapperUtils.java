package com.github.summerframework.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by renan on 20/02/16.
 */
public class ObjectMapperUtils {

    static ObjectMapper objectMapper;
    static{
        objectMapper =
                new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ObjectMapper get (){
        return objectMapper;
    }

    public static Object parse (Object o, Class<?> clazz) throws IOException {

        final byte[] json = objectMapper.writeValueAsBytes(o);
        return objectMapper.readValue(json, clazz);
    }
}

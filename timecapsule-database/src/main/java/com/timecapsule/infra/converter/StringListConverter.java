package com.timecapsule.infra.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return String.join(",", list); // => <= >=
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return new ArrayList<>(Arrays.asList(dbData.split(",")));
    }
}

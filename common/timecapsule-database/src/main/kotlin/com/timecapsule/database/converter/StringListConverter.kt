package com.timecapsule.database.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(list: List<String>): String = list.joinToString(",")

    override fun convertToEntityAttribute(dbData: String): List<String> = dbData.split(",")
}

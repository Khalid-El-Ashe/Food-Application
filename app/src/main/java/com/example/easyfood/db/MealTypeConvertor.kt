package com.example.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

// TODO: this class to make change from DataType to DataType like ( from Any to String )
@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String{
        if (attribute == null){
            return ""
        } else {
            return attribute.toString()
        }
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any{
        if (attribute == null){
            return ""
        } else {
            return attribute.toString()
        }
    }
}
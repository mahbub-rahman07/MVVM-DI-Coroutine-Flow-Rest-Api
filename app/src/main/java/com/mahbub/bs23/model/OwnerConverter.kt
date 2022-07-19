package com.mahbub.bs23.model

import android.text.TextUtils
import com.google.gson.Gson
import io.objectbox.converter.PropertyConverter

class OwnerConverter: PropertyConverter<Owner, String> {
    override fun convertToEntityProperty(databaseValue: String?): Owner? {
        if (!TextUtils.isEmpty(databaseValue)) {
            val gson = Gson()
            return gson.fromJson(databaseValue, Owner::class.java)
        }
        return null
    }

    override fun convertToDatabaseValue(entityProperty: Owner?): String {
        val gson = Gson()
        return gson.toJson(entityProperty)
    }
}
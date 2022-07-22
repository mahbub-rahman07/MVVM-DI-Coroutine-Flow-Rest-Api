package com.mahbub.rest_mvvm.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getDateTime(updatedDate: String): String {

        var date = SimpleDateFormat(ISO_DATE_FORMAT).parse(updatedDate)
        val df = SimpleDateFormat(OUT_DATE_FORMAT, Locale.getDefault())
        return date?.let { df.format(it) }?:"Unsupported date format!"

    }


}
package com.mahbub.rest_mvvm.app

import android.content.Context
import com.mahbub.rest_mvvm.model.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}
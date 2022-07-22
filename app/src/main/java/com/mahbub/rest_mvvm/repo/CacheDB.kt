package com.mahbub.rest_mvvm.repo

import com.mahbub.rest_mvvm.model.Item
import io.objectbox.Box
import javax.inject.Inject

class CacheDB @Inject constructor(private val itemBox: Box<Item>) {

    fun saveItems(itemList:List<Item>) {
        itemBox.removeAll()
        itemBox.put(itemList)
    }
    fun getItems(): MutableList<Item> {
        return itemBox.query().build().find()
    }
 }
package com.mahbub.bs23.repo

import com.mahbub.bs23.model.Item
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
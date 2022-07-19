package com.mahbub.bs23.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Item(
    @Id var itemId: Long,
    val created_at: String,
    val description: String?,
    val full_name: String,
    val id: Int,
    val name: String,
    @Convert(converter = OwnerConverter::class, dbType = String::class)
    val owner: Owner,
    val pushed_at: String,
    val score: Int,
    val size: Int,
    val stargazers_count: Int,
    val updated_at: String,
    val watchers: Int,
    val watchers_count: Int
)
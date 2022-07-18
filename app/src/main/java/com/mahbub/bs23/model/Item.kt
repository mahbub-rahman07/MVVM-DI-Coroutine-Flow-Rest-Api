package com.mahbub.bs23.model

data class Item(
    val created_at: String,
    val description: String,
    val full_name: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val pushed_at: String,
    val score: Int,
    val size: Int,
    val stargazers_count: Int,
    val updated_at: String,
    val watchers: Int,
    val watchers_count: Int
)
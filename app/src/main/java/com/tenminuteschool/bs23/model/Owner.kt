package com.tenminuteschool.bs23.model

data class Owner(
    val avatar_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val site_admin: Boolean,
    val type: String
)
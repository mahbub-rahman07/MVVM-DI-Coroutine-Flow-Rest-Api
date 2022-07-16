package com.tenminuteschool.bs23.model

data class TopRepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)
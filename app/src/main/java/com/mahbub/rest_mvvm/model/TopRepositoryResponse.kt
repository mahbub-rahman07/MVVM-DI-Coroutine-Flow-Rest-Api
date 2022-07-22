package com.mahbub.rest_mvvm.model

data class TopRepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)
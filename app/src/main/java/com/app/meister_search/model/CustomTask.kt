package com.app.meister_search.model

data class CustomTask(
    var taskId: Int?,
    val taskName: String,
    val projectName: String,
    val status: Boolean
)

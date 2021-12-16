package com.app.meister_search.model


import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("projects")
    val projects: List<Project>?,
    @SerializedName("sections")
    val sections: List<Section>?,
    @SerializedName("tasks")
    val tasks: List<Task>?
)
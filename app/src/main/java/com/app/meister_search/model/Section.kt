package com.app.meister_search.model


import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("indicator")
    val indicator: Int?,
    @SerializedName("limit")
    val limit: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("object_actions_count")
    val objectActionsCount: Int?,
    @SerializedName("project_id")
    val projectId: Int?,
    @SerializedName("recurring_actions_count")
    val recurringActionsCount: Int?,
    @SerializedName("sequence")
    val sequence: Double?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
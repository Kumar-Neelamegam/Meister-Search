package com.app.meister_search.model


import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mail_token")
    val mailToken: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("notes")
    val notes: Any?,
    @SerializedName("roles_enabled")
    val rolesEnabled: Boolean?,
    @SerializedName("share_mode")
    val shareMode: Int?,
    @SerializedName("share_token")
    val shareToken: Any?,
    @SerializedName("share_token_enabled")
    val shareTokenEnabled: Boolean?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("tasks_active_count")
    val tasksActiveCount: Int?,
    @SerializedName("tasks_archive_count")
    val tasksArchiveCount: Int?,
    @SerializedName("tasks_complete_count")
    val tasksCompleteCount: Int?,
    @SerializedName("tasks_trash_count")
    val tasksTrashCount: Int?,
    @SerializedName("team_id")
    val teamId: Int?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: Any?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
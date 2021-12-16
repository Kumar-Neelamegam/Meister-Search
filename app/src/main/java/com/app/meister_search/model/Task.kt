package com.app.meister_search.model


import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("assigned_to_id")
    val assignedToId: Any?,
    @SerializedName("attachment_id")
    val attachmentId: Any?,
    @SerializedName("attachments_count")
    val attachmentsCount: Int?,
    @SerializedName("closed_cl_items_count")
    val closedClItemsCount: Int?,
    @SerializedName("comments_count")
    val commentsCount: Int?,
    @SerializedName("completed_at")
    val completedAt: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("created_by_id")
    val createdById: Int?,
    @SerializedName("created_by_origin")
    val createdByOrigin: String?,
    @SerializedName("due")
    val due: Any?,
    @SerializedName("flagged")
    val flagged: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("meta_information")
    val metaInformation: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("notes")
    val notes: Any?,
    @SerializedName("priority")
    val priority: Any?,
    @SerializedName("reminder")
    val reminder: Any?,
    @SerializedName("repeat")
    val repeat: Any?,
    @SerializedName("section_id")
    val sectionId: Int?,
    @SerializedName("sequence")
    val sequence: Double?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("status_changed_by_id")
    val statusChangedById: Int?,
    @SerializedName("status_updated_at")
    val statusUpdatedAt: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("total_cl_items_count")
    val totalClItemsCount: Int?,
    @SerializedName("tracked_time")
    val trackedTime: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
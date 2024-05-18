package com.jeizard.quizapp.data.supabase.models.notes.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteSBEntity(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String
)
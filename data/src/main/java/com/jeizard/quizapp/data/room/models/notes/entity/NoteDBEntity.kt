package com.jeizard.quizapp.data.room.models.notes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes"
)
data class NoteDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String)


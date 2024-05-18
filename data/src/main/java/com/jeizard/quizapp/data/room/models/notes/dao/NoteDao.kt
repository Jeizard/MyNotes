package com.jeizard.quizapp.data.room.models.notes.dao

import androidx.room.Dao
import androidx.room.Query
import com.jeizard.quizapp.data.room.base.BaseDao
import com.jeizard.quizapp.data.room.models.notes.entity.NoteDBEntity

@Dao
abstract class NoteDao : BaseDao<NoteDBEntity> {

    @Query("DELETE FROM notes")
    abstract fun deleteAllNotes()

    @Query("SELECT * FROM notes")
    abstract fun getAllNotes(): List<NoteDBEntity>

    override fun deleteAll() {
        deleteAllNotes()
    }

    override fun getAll(): List<NoteDBEntity> {
        return getAllNotes()
    }
}

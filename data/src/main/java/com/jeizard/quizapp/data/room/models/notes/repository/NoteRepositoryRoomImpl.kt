package com.jeizard.quizapp.data.room.models.tests.repository

import android.app.Application
import com.jeizard.domain.entities.Note
import com.jeizard.domain.repositories.BaseRepository
import com.jeizard.quizapp.data.room.AppDatabase
import com.jeizard.quizapp.data.room.base.BaseRepositoryRoomImpl
import com.jeizard.quizapp.data.room.models.notes.dao.NoteDao
import com.jeizard.quizapp.data.room.models.notes.entity.NoteDBEntity
import com.jeizard.quizapp.data.room.models.notes.mapper.NoteDBEntityMapper

class NoteRepositoryRoomImpl(application: Application) :
    BaseRepositoryRoomImpl<NoteDBEntity, NoteDao, Note>(
        AppDatabase.getInstance(application).noteDao(),
        NoteDBEntityMapper()
    ),
    BaseRepository<Note> {
}
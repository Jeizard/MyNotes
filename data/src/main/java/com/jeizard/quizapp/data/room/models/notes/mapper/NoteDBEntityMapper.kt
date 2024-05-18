package com.jeizard.quizapp.data.room.models.notes.mapper

import com.jeizard.domain.entities.Note
import com.jeizard.quizapp.data.mapper.Mapper
import com.jeizard.quizapp.data.room.models.notes.entity.NoteDBEntity

class NoteDBEntityMapper : Mapper<NoteDBEntity, Note> {

    override fun mapFromDBEntity(d: NoteDBEntity): Note {
        return Note(d.id, d.title, d.description)
    }

    override fun mapToDBEntity(e: Note): NoteDBEntity {
        return NoteDBEntity(e.id, e.title, e.description)
    }

    override fun mapFromDBEntity(dCollection: Collection<NoteDBEntity>): List<Note> {
        return dCollection.map { mapFromDBEntity(it) }
    }

    override fun mapToDBEntity(eCollection: Collection<Note>): List<NoteDBEntity> {
        return eCollection.map { mapToDBEntity(it) }
    }
}


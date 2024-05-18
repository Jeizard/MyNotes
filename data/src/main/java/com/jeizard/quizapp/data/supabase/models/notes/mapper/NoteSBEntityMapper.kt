package com.jeizard.quizapp.data.supabase.models.notes.mapper

import com.jeizard.domain.entities.Note
import com.jeizard.quizapp.data.mapper.Mapper
import com.jeizard.quizapp.data.supabase.models.notes.entity.NoteSBEntity

class NoteSBEntityMapper : Mapper<NoteSBEntity, Note> {

    override fun mapFromDBEntity(d: NoteSBEntity): Note {
        return Note(d.id, d.title, d.description)
    }

    override fun mapToDBEntity(e: Note): NoteSBEntity {
        return NoteSBEntity(e.id, e.title, e.description)
    }

    override fun mapFromDBEntity(dCollection: Collection<NoteSBEntity>): List<Note> {
        return dCollection.map { mapFromDBEntity(it) }
    }

    override fun mapToDBEntity(eCollection: Collection<Note>): List<NoteSBEntity> {
        return eCollection.map { mapToDBEntity(it) }
    }
}


package com.jeizard.quizapp.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeizard.domain.entities.Note
import com.jeizard.domain.repositories.BaseRepository
import com.jeizard.quizapp.data.room.models.tests.repository.NoteRepositoryRoomImpl

class NoteViewModel(private val noteRepository: BaseRepository<Note>) : ViewModel() {
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val noteRepository: BaseRepository<Note> = NoteRepositoryRoomImpl(application)
            return NoteViewModel(noteRepository) as T
        }
    }
    val selectedNote = MutableLiveData<Note>()
    val allNotes: MutableLiveData<List<Note>> = MutableLiveData()

    private val notesListener = object : BaseRepository.OnDataChangedListener<Note> {
        override fun onChanged(items: List<Note>) {
            allNotes.postValue(items)
        }
    }

    init{
        noteRepository.addListener(notesListener)
    }

    fun selectNote(note: Note) {
        selectedNote.value = note
    }

    fun updateNote(){
        noteRepository.update(selectedNote.value!!)
    }
    fun createNote(){
        noteRepository.insert(selectedNote.value!!)
    }
    fun deleteNotes(noteIndices: List<Int>){
        noteIndices.forEach { noteIndex ->
            noteRepository.delete(allNotes.value!![noteIndex])
        }
    }
}
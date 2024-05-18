package com.jeizard.quizapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jeizard.domain.entities.Note
import com.jeizard.quizapp.databinding.ItemNoteBinding

class NoteAdapter(private val onItemClicked: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    val selectedIndices: MutableList<Int> = mutableListOf<Int>()
    val isSelectedMode: MutableLiveData<Boolean> = MutableLiveData(false)

    var notesList: List<Note> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            note: Note,
            onItemClicked: (note: Note) -> Unit
        ) {

            if(note.title != ""){
                binding.titleTextView.text = note.title
            }
            binding.descriptionTextView.text = note.description
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(notesList[position], onItemClicked)

        if(selectedIndices.contains(position)){
            viewHolder.binding.noteCardView.strokeColor = Color.BLUE
        }
        else{
            viewHolder.binding.noteCardView.strokeColor = Color.BLACK
        }

        if(isSelectedMode.value!!){
            viewHolder.binding.noteCardView.setOnClickListener {
                if(selectedIndices.contains(position)){
                    selectedIndices.remove(position)
                }
                else{
                    selectedIndices.add(position)
                }

                // viewHolder.binding.noteCardView.isSelected = !viewHolder.binding.noteCardView.isSelected
                notifyItemChanged(position)
            }
        }
        else{
            //viewHolder.binding.noteCardView.isSelected = false
            viewHolder.binding.noteCardView.setOnClickListener{ onItemClicked(notesList[position]) }
        }

        viewHolder.binding.noteCardView.setOnLongClickListener{
            selectedIndices.add(position)
            isSelectedMode.value = true
            notifyDataSetChanged()
            true
        }
    }

    override fun getItemCount(): Int = notesList.size

    fun disableDeleteMode(){
        selectedIndices.clear()
        isSelectedMode.value = false
        notifyDataSetChanged()
    }
}
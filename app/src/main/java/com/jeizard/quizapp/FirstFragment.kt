package com.jeizard.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeizard.domain.entities.Note
import com.jeizard.quizapp.adapters.NoteAdapter
import com.jeizard.quizapp.databinding.FragmentFirstBinding
import com.jeizard.quizapp.viewModels.NoteViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by activityViewModels() {NoteViewModel.Factory(requireActivity().application)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.notesRecyclerView
        val noteAdapter = NoteAdapter(
            onItemClicked =  {note ->
                noteViewModel.selectNote(note)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            })

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = noteAdapter

        noteViewModel.allNotes.observe(requireActivity(), Observer {notes ->
            noteAdapter.notesList = notes
        })

        noteAdapter.isSelectedMode.observe(requireActivity(), Observer {
            if(noteAdapter.selectedIndices.isNotEmpty()){
                binding.backButton.visibility = View.VISIBLE
                binding.backButton.setOnClickListener(View.OnClickListener {
                    noteAdapter.disableDeleteMode()
                })
                binding.addNewNoteButton.setImageResource(R.drawable.ic_delete)
                binding.addNewNoteButton.setOnClickListener(View.OnClickListener {
                    noteViewModel.deleteNotes(noteAdapter.selectedIndices)
                    noteAdapter.disableDeleteMode()
                })
            }
            else{
                binding.backButton.visibility = View.GONE
                binding.addNewNoteButton.setImageResource(R.drawable.ic_add)
                binding.addNewNoteButton.setOnClickListener(View.OnClickListener {
                    noteViewModel.selectNote(Note(0, "", ""))
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                })
            }
        })
    }
}
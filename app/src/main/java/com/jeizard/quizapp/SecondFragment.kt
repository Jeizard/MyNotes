package com.jeizard.quizapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jeizard.domain.entities.Note
import com.jeizard.quizapp.databinding.FragmentSecondBinding
import com.jeizard.quizapp.viewModels.NoteViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by activityViewModels() { NoteViewModel.Factory(requireActivity().application)}

    var createdNote = Note(0, "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel.selectedNote.observe(requireActivity(), Observer {
            binding.titleEditText.setText(noteViewModel.selectedNote.value!!.title)
            binding.descriptionEditText.setText(noteViewModel.selectedNote.value!!.description)
        })

        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                noteViewModel.selectedNote.value!!.title = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.descriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                noteViewModel.selectedNote.value!!.description = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.backButton.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
    }

    override fun onPause() {
        super.onPause()

        if(noteViewModel.selectedNote.value!!.id == 0L){
            if(noteViewModel.selectedNote.value!!.title != "" || noteViewModel.selectedNote.value!!.description != "") {
                noteViewModel.createNote()
            }
        }
        else{
            noteViewModel.updateNote()
        }
    }
}
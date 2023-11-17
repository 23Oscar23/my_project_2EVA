package es.otm.myproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.otm.myproject.databinding.FragmentToolsBinding

class ToolsFragment : Fragment() {

    private lateinit var binding: FragmentToolsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToolsBinding.inflate(inflater, container, false)

        binding.btnCerrarSesion.setOnClickListener{
            SecondDialogFragment().show(requireActivity().supportFragmentManager, "")
        }
        return binding.root
    }

}
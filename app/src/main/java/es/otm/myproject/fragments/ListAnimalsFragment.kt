package es.otm.myproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.otm.myproject.R
import es.otm.myproject.adapters.AnimalesAdapter
import es.otm.myproject.classes.Animales
import es.otm.myproject.databinding.FragmentListAnimalsBinding

class ListAnimalsFragment : Fragment() {

    private lateinit var binding: FragmentListAnimalsBinding
    private lateinit var mListener: FragmentListAnimalsListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentListAnimalsListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListAnimalsBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            Snackbar.make(binding.coordinator, "Button to Create a New Pet", Snackbar.LENGTH_SHORT)
                .setAction("CREATE PET", View.OnClickListener {
                    FirstDialogFragment().show(requireActivity().supportFragmentManager, "")
                })
                .show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val animal = mutableListOf<Animales>(
            Animales("Dog", R.drawable.dog_image),
            Animales("Cat", R.drawable.cat_image),
            Animales("Rodents", R.drawable.rabbit_image),
            Animales("Bird", R.drawable.bird_image)
        )

        val animalClickFunction = { animales: Animales ->
            Toast.makeText(requireContext(), animales.name, Toast.LENGTH_SHORT).show()
            mListener.onAnimalClicked(animales.name)
        }

        val animalesAdapter = AnimalesAdapter(requireContext(), animal, animalClickFunction)

        binding.recView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.recView.adapter = animalesAdapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    interface FragmentListAnimalsListener{
        fun onAnimalClicked(animalName : String)
    }
}
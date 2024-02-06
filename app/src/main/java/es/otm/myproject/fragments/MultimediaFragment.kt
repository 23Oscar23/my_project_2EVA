package es.otm.myproject.fragments

import android.content.Context
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import es.otm.myproject.R
import es.otm.myproject.databinding.FragmentMultimediaBinding

class MultimediaFragment : Fragment() {

    private lateinit var binding: FragmentMultimediaBinding
    private lateinit var mListener: FragmentMultimediaListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMultimediaBinding.inflate(layoutInflater)

        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            mListener.onBottomClick(item)
            true
        }

        return binding.root
    }

    interface FragmentMultimediaListener{
        fun onBottomClick(item: MenuItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentMultimediaListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }

}
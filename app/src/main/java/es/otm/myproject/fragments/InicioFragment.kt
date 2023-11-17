package es.otm.myproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.otm.myproject.R
import es.otm.myproject.databinding.FragmentInicioBinding

class InicioFragment : Fragment(), View.OnClickListener {

    private var mListener: OnInicioFragmentListener? = null
    private lateinit var binding: FragmentInicioBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInicioFragmentListener){
            mListener = context
        }
        else{
            throw Exception("The activity must implement the interface OnInicioFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)

        binding.btnStart.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View){
        when(view.id){
            R.id.btnStart -> mListener?.onInicioButtonClick()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnInicioFragmentListener{
        fun onInicioButtonClick()
    }

}
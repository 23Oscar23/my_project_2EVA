package es.otm.myproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import es.otm.myproject.AuthManager
import es.otm.myproject.R
import es.otm.myproject.databinding.FragmentResetPassBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPassFragment : Fragment() {

    private lateinit var binding: FragmentResetPassBinding
    private lateinit var mListener: FragmentResetPassListener
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPassBinding.inflate(inflater, container, false)

        binding.btnUpdate.setOnClickListener{
            val email = binding.userRegister.text.toString()

            if (!email.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO){
                    authManager.rememberPass(email)
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), "Email Send", Toast.LENGTH_SHORT).show()
                        mListener.onUpdatebtnClick()
                    }
                }
            }
        }

        return binding.root
    }

    interface FragmentResetPassListener{
        fun onUpdatebtnClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authManager = AuthManager()
        if (context is FragmentResetPassListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }
}
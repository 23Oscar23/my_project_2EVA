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
import es.otm.myproject.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mListener: FragmentRegisterListener
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnAcces.setOnClickListener{
            val email = binding.userRegister.text.toString()
            val pass = binding.userPass.text.toString()
            val confirmar = binding.confirmPass.text.toString()
            if (!email.isNullOrBlank() && !pass.isNullOrBlank() && confirmar.equals(pass, ignoreCase = true)){
                lifecycleScope.launch(Dispatchers.IO){
                    val userRegister = authManager.register(email, pass)
                    withContext(Dispatchers.Main){
                        if (userRegister != null){
                            Toast.makeText(requireContext(), "User Register", Toast.LENGTH_SHORT).show()
                            mListener.onAccesbtnClick()
                        }
                        else{
                            Toast.makeText(requireContext(), "Bad credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    interface FragmentRegisterListener{
        fun onAccesbtnClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentRegisterListener){
            mListener = context
            authManager = AuthManager()
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }
}
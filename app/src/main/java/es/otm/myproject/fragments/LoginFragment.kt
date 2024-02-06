package es.otm.myproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import es.otm.myproject.AuthManager
import es.otm.myproject.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mListener: FragmentLoginListener
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        binding.btnLogin.setOnClickListener{
            val user = binding.textUser.editText?.text.toString()
            val pasword = binding.textPassword.editText?.text.toString()

            if (!user.isNullOrBlank() && !pasword.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO){
                    val userLogged = authManager.login(user, pasword)
                    withContext(Dispatchers.Main){
                        if (userLogged != null){
                            Toast.makeText(requireContext(), userLogged.email, Toast.LENGTH_SHORT).show()
                            mListener.onLoginClicked()
                        }
                        else{
                            Toast.makeText(requireContext(), "Bad credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnRegistrarse.setOnClickListener{
            mListener.onRegisterClicked()
        }

        binding.textRecordar?.setOnClickListener {
            mListener.onRecordarClicked()
        }

        return binding.root
    }

    interface FragmentLoginListener{
        fun onLoginClicked()
        fun onRegisterClicked()
        fun onRecordarClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentLoginListener){
            mListener = context
            authManager = AuthManager()
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }
}
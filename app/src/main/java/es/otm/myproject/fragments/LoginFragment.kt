package es.otm.myproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import es.otm.myproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mListener: FragmentLoginListener
    private var pulsado = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener{
            val user = binding.textUser.editText?.text.toString()
            val pasword = binding.textPassword.editText?.text.toString()

            if (pulsado || (user.equals("Oscar") && pasword.equals("1234"))){
                mListener.onLoginClicked()
            }else{
                Snackbar.make(binding.root, "First do you have to register", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnRegistrarse.setOnClickListener{
            val user = binding.textUser.editText?.text.toString()
            val pasword = binding.textPassword.editText?.text.toString()

            if (user.isEmpty() || pasword.isEmpty()){
                Snackbar.make(binding.root, "Information is not complet", Snackbar.LENGTH_SHORT).show()
            }else{
                pulsado = true
                mListener.onRegisterClicked()
            }
        }
        return binding.root
    }

    interface FragmentLoginListener{
        fun onLoginClicked()
        fun onRegisterClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentLoginListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }
}
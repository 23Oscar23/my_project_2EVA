package es.otm.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import es.otm.myproject.databinding.ActivityMainBinding
import es.otm.myproject.fragments.InicioFragment
import es.otm.myproject.fragments.LoginFragment
import es.otm.myproject.fragments.RegisterFragment
import es.otm.myproject.fragments.ResetPassFragment

class LoginActivity : AppCompatActivity(), InicioFragment.OnInicioFragmentListener, LoginFragment.FragmentLoginListener, RegisterFragment.FragmentRegisterListener, ResetPassFragment.FragmentResetPassListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onLoginClicked() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onInicioButtonClick() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<LoginFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }

    override fun onRecordarClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<ResetPassFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }

    override fun onRegisterClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<RegisterFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }

    override fun onAccesbtnClick() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<LoginFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }

    override fun onUpdatebtnClick() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<LoginFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }
}
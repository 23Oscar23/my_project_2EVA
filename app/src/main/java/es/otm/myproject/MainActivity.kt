package es.otm.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.ListFragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.snackbar.Snackbar
import es.otm.myproject.databinding.ActivityMainBinding
import es.otm.myproject.fragments.InicioFragment
import es.otm.myproject.fragments.LoginFragment

class MainActivity : AppCompatActivity(), InicioFragment.OnInicioFragmentListener, LoginFragment.FragmentLoginListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onInicioButtonClick() {
        supportFragmentManager.commit {
            setReorderingAllowed(false)
            replace<LoginFragment>(R.id.fragment_container)
            addToBackStack(null)
        }
    }

    override fun onRegisterClicked() {
        Snackbar.make(binding.root, "User successfully registered", Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoginClicked() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}
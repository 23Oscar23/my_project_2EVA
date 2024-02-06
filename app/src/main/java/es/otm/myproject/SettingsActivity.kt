package es.otm.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.otm.myproject.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

     companion object{
        val USERNAME = "signature"
        val COLOR = "color_preferences"
        val OFFLINE = "offline_mode"
        val VIBRAR = "vibrate"
    }
}
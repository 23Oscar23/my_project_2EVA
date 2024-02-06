package es.otm.myproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView
import es.otm.myproject.databinding.ActivityListBinding
import es.otm.myproject.databinding.ActivityMainBinding
import es.otm.myproject.fragments.*

class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ListAnimalsFragment.FragmentListAnimalsListener, SecondDialogFragment.SecondDialogListener, MultimediaFragment.FragmentMultimediaListener {

    private lateinit var binding: ActivityListBinding
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.myToolbar)

        askNotificationPermission()
        setUpNavigationDrawer()
    }

    fun askNotificationPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                21
            )
        }
    }

    override fun onResume() {
        super.onResume()
        pref = getSharedPreferences("es.otm.myproject_preferences", Context.MODE_PRIVATE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpNavigationDrawer() {
        val toogle = ActionBarDrawerToggle (
            this,
            binding.drawerLayout,
            binding.myToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close,
        )
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings ->{
                SecondDialogFragment().show(this.supportFragmentManager, "")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return when(item.itemId){
            R.id.nav_pets ->{
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, ListAnimalsFragment())
                    .commit()
                true
            }
            R.id.nav_camera -> {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, CameraFragment())
                    .commit()
                true
            }
            R.id.nav_tools -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_retrofit -> {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, RetrofitFragment())
                    .commit()
                true
            }
            R.id.nav_chat -> {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, MensajesFragment())
                    .commit()
                true
            }
            R.id.nav_multimedia -> {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, MultimediaFragment())
                    .commit()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onAnimalClicked(animalName: String) {
        if (animalName == "Dog") {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<DogFragment>(R.id.myContent)
                addToBackStack(null)
            }
        } else if (animalName == "Cat") {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<CatFragment>(R.id.myContent)
                addToBackStack(null)
            }
        } else if (animalName == "Rodents") {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<RodentsFragment>(R.id.myContent)
                addToBackStack(null)
            }
        } else if (animalName == "Bird") {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<BirdFragment>(R.id.myContent)
                addToBackStack(null)
            }
        }
        else{
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MyPetFragment>(R.id.myContent)
                addToBackStack(null)
            }
        }
    }

    override fun onYesClick() {
        AuthManager().logOut()
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBottomClick(item: MenuItem) {
        when (item.itemId) {
            R.id.audio -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<AudioFragment>(R.id.containerMultimedia)
                    addToBackStack(null)
                }
                true
            }
            R.id.video -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<VideoFragment>(R.id.containerMultimedia)
                    addToBackStack(null)
                }
                true
            }
            else -> false
        }
    }

}
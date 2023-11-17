package es.otm.myproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import es.otm.myproject.databinding.ActivityListBinding
import es.otm.myproject.fragments.*

class ListActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ListAnimalsFragment.FragmentListAnimalsListener, FirstDialogFragment.FirstDialogListener, SecondDialogFragment.SecondDialogListener {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.myToolbar)

        setUpNavigationDrawer()

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
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, ToolsFragment())
                    .commit()
                true
            }
            R.id.nav_share-> {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.myContent, ShareFragment())
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
        } else {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<BirdFragment>(R.id.myContent)
                addToBackStack(null)
            }
        }
    }

    override fun onDialogClick() {
        Snackbar.make(binding.root, "Pet Create Correctly", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCancelClick() {
        Snackbar.make(binding.root, "Creation Pet Cancel", Snackbar.LENGTH_SHORT).show()
    }

    override fun onYesClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
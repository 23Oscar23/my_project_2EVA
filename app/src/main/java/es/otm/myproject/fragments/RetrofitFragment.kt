package es.otm.myproject.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import es.otm.myproject.CatService
import es.otm.myproject.R
import es.otm.myproject.RetrofitObject
import es.otm.myproject.adapters.CatsAdapter
import es.otm.myproject.database.Cat
import es.otm.myproject.database.CatDB
import es.otm.myproject.databinding.ActivityListBinding
import es.otm.myproject.databinding.FragmentRetrofitBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.room.Room
import es.otm.myproject.SettingsActivity

class RetrofitFragment : Fragment() {

    private lateinit var binding: FragmentRetrofitBinding
    private lateinit var mAdapter: CatsAdapter
    private lateinit var pref : SharedPreferences
    private var listCats: MutableList<String> = mutableListOf()
    private var descriptions: MutableList<String> = mutableListOf()
    private var cats = mutableListOf<Cat>()
    private lateinit var database: CatDB
    private var lastBreed: String = ""
    private var comprobarConexion: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRetrofitBinding.inflate(inflater, container, false)

        database = Room.databaseBuilder(
            requireContext(),
            CatDB::class.java,
            "CatDB"
        ).build()

        pref = requireContext().getSharedPreferences("es.otm.myproject_preferences", Context.MODE_PRIVATE)
        comprobarConexion = pref.getBoolean(SettingsActivity.OFFLINE, true)

        binding.button.setOnClickListener{
            if (comprobarConexion){
                Toast.makeText(requireContext(), "Offline Mode Is Activated", Toast.LENGTH_SHORT).show()
                showDatabase()
            }
            else {
                val breed = binding.editText.text.toString()
                if (!breed.isNullOrEmpty()) {
                    lastBreed = breed
                    searchCatByBreed(breed)
                    binding.editText.text.clear()
                } else {
                    Toast.makeText(requireContext(), "Enter a cat breed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonNext.setOnClickListener {
            if (lastBreed.isNotEmpty()) {
                searchCatByBreed(lastBreed)
            } else {
                Toast.makeText(requireContext(), "No previous breed searched", Toast.LENGTH_SHORT).show()
            }
        }

        setUpRecycler(comprobarConexion)
        return binding.root
    }

    private fun setUpRecycler(isOffline: Boolean){
        val spanCount: Int
        if (isOffline){
            spanCount = 2
        }
        else{
            spanCount = 1
        }
        mAdapter = CatsAdapter(listCats, descriptions)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.recyclerView.adapter = mAdapter
    }

    private fun searchCatByBreed(breedName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val breedResponse = RetrofitObject.getInstance().create(CatService::class.java).getBreed(breedName)
                if (breedResponse.isSuccessful) {
                    val breeds = breedResponse.body()
                    if (!breeds.isNullOrEmpty()) {
                        val breed = breeds.firstOrNull { it.name.equals(breedName, ignoreCase = true) }
                        if (breed != null) {
                            val breedId = breed.id
                            val breedDescription = breed.description
                            val catResponse = RetrofitObject.getInstance().create(CatService::class.java).getCats(breedId)
                            if (catResponse.isSuccessful) {
                                val cats = catResponse.body()
                                if (!cats.isNullOrEmpty()) {
                                    listCats.clear()
                                    descriptions.clear()
                                    listCats.addAll(cats.map { cat -> cat.url })
                                    descriptions.addAll(cats.map { cat -> breedDescription ?: "" })

//                                    if (comprobarConexion.equals("true")) {
//                                        CatDB.getInstance(requireContext()).catDAO().insert(
//                                            Cat(
//                                                breed = breedName,
//                                                description = breedDescription ?: ""
//                                            )
//                                        )
//                                    }
                                    withContext(Dispatchers.Main) {
                                        mAdapter.notifyDataSetChanged()
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "No images found for this breed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to get images for this breed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Breed not found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Breed not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get breed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.toString()
            }
        }
    }

    private fun showDatabase(){
        lifecycleScope.launch(Dispatchers.IO){
            val cats = database.catDAO().getAll()

            withContext(Dispatchers.Main){
                listCats.clear()
                descriptions.clear()
                listCats.addAll(cats.map { cat -> cat.breed })
                descriptions.addAll(cats.map { cat -> cat.description })
                mAdapter.notifyDataSetChanged()
            }
        }
    }

}
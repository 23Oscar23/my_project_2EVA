package es.otm.myproject.fragments

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
import es.otm.myproject.databinding.ActivityListBinding
import es.otm.myproject.databinding.FragmentRetrofitBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitFragment : Fragment() {

    private lateinit var binding: FragmentRetrofitBinding
    private lateinit var mAdapter: CatsAdapter
    private var listCats: MutableList<String> = mutableListOf()
    private var descriptions: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRetrofitBinding.inflate(layoutInflater)

        binding.button.setOnClickListener{
            val breed = binding.editText.text.toString()
            if (!breed.isNullOrEmpty()) {
                searchCatByBreed(breed)
                binding.editText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter a cat breed", Toast.LENGTH_SHORT).show()
            }
        }

        setUpRecycler()
        return binding.root
    }

    private fun setUpRecycler(){
        mAdapter = CatsAdapter(listCats, descriptions)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = mAdapter
    }

    private fun searchCatByBreed(breedName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val breedResponse = RetrofitObject.getInstance().create(CatService::class.java).getBreed(breedName)
                if (breedResponse.isSuccessful) {
                    val breedId = breedResponse.body()?.firstOrNull()?.id
                    val breedDescription = breedResponse.body()?.firstOrNull()?.description
                    if (breedId != null) {
                        val catResponse = RetrofitObject.getInstance().create(CatService::class.java).getCats(breedId)
                        withContext(Dispatchers.Main) {
                            if (catResponse.isSuccessful) {
                                val cats = catResponse.body()
                                if (!cats.isNullOrEmpty()) {
                                    listCats.clear()
                                    descriptions.clear()
                                    listCats.addAll(cats.map { cat -> cat.url })
                                    descriptions.addAll(cats.map { cat -> breedDescription ?: "" })
                                    mAdapter.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(requireContext(), "No cats found for this breed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to get cats", Toast.LENGTH_SHORT).show()
                            }
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

}
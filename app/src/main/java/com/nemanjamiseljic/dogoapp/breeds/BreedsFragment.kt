package com.nemanjamiseljic.dogoapp.breeds

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nemanjamiseljic.dogoapp.R
import com.nemanjamiseljic.dogoapp.databinding.BreedsFragmentBinding
import com.nemanjamiseljic.dogoapp.models.breeds.BreedsItem
import com.nemanjamiseljic.dogoapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private val TAG by lazy { "BreedsFragment" }

@AndroidEntryPoint
class BreedsFragment @Inject constructor(

) : Fragment(R.layout.breeds_fragment), AdapterView.OnItemSelectedListener {

    private lateinit var binding: BreedsFragmentBinding
    private lateinit var arrayAdapter: ArrayAdapter<BreedsItem>
    private val viewModel: BreedViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var recyclerViewAdapter: BreedsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BreedsFragmentBinding.bind(view)
        binding.breedsSpinner.onItemSelectedListener = this



        recyclerView = binding.breedRecyclerview
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = recyclerViewAdapter
        }




        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getDogsBreeds().collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.apply {
                            breedsProgressSpinner.visibility = View.VISIBLE
                            breedsTvFetchingBreeds.visibility = View.VISIBLE
                            breedsTvSelectDogBreed.visibility = View.INVISIBLE
                            breedsSpinner.visibility = View.INVISIBLE
                            breedsTvErrorFetchingBreeds.visibility = View.INVISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            breedsProgressSpinner.visibility = View.INVISIBLE
                            breedsTvFetchingBreeds.visibility = View.INVISIBLE
                            breedsTvSelectDogBreed.visibility = View.INVISIBLE
                            breedsSpinner.visibility = View.INVISIBLE
                            breedsTvErrorFetchingBreeds.visibility = View.VISIBLE
                        }
                    }
                    Status.SUCCESS -> {
                        viewModel.setCurrentlyLoadedDogBreeds(breeds = it.data!!)
                        arrayAdapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            it.data!!
                        )
                        binding.apply {
                            breedsProgressSpinner.visibility = View.INVISIBLE
                            breedsTvFetchingBreeds.visibility = View.INVISIBLE
                            breedsTvSelectDogBreed.visibility = View.VISIBLE
                            breedsSpinner.visibility = View.VISIBLE
                            breedsTvErrorFetchingBreeds.visibility = View.INVISIBLE
                            breedsSpinner.adapter = arrayAdapter
                        }
                    }
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        Log.d(TAG, "onItemSelected: ")
        /**IF we want to preserve data on rotate we would most likely implement custom array adapter**/
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            if(viewModel.lastLoadedDogBreedId.value != viewModel.currentLoadedDogBreeds.value[p2].id){
                viewModel.getSelectedDogBreed(
                    breedId = viewModel.currentLoadedDogBreeds.value[p2].id,
                    limit = 100
                ).collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            recyclerViewAdapter.breedsList = it.data!!
                        }
                        Status.LOADING -> {
                            //TODO

                        }
                        Status.ERROR -> {
                            //TODO
                        }
                    }
                }

                viewModel.lastLoadedDogBreedId.emit(viewModel.currentLoadedDogBreeds.value[p2].id)
            }


        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}
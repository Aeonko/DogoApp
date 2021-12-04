package com.nemanjamiseljic.dogoapp.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemanjamiseljic.dogoapp.models.breeds.Breeds
import com.nemanjamiseljic.dogoapp.models.breeds.BreedsItem
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreed
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreedItem
import com.nemanjamiseljic.dogoapp.repo.Repository
import com.nemanjamiseljic.dogoapp.util.Resource
import com.nemanjamiseljic.dogoapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel  @Inject constructor(
    private val repository: Repository,

): ViewModel()
{

    private val _currentLoadedDogBreed = MutableStateFlow(Breeds())
    val currentLoadedDogBreeds: StateFlow<Breeds> = _currentLoadedDogBreed
    fun setCurrentlyLoadedDogBreeds(breeds: Breeds){
        viewModelScope.launch {
            _currentLoadedDogBreed.emit(breeds)
        }
    }

    var lastLoadedDogBreedId = MutableStateFlow(-1)

    suspend fun getDogsBreeds() = repository.getDogsBreeds()


    suspend fun getSelectedDogBreed(breedId: Int,limit: Int) = repository.getSelectedDogBreed(breedId = breedId, limit = limit)



}
package com.nemanjamiseljic.dogoapp.repo

import com.nemanjamiseljic.dogoapp.models.breeds.Breeds
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreed
import com.nemanjamiseljic.dogoapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getDogsBreeds(): Flow<Resource<Breeds>>

    suspend fun getSelectedDogBreed(breedId: Int, limit: Int): Flow<Resource<SelectedBreed>>

}
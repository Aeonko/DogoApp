package com.nemanjamiseljic.dogoapp.repo


import android.util.Log
import com.nemanjamiseljic.dogoapp.api.RetrofitAPI
import com.nemanjamiseljic.dogoapp.models.breeds.Breeds
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreed
import com.nemanjamiseljic.dogoapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

private val TAG by lazy { Repository::class.java.name }

class Repository @Inject constructor(
    private val retrofitApi: RetrofitAPI
) : RepositoryInterface {
    override suspend fun getDogsBreeds(): Flow<Resource<Breeds>> {
        return flow {
            emit(Resource.loading(data = null))
            try {

                val response = retrofitApi.getBreeds()
                Resource.loading(null)
                emit(
                    if (response.isSuccessful) {
                        response.body()?.let { Resource.success(data = it) } ?: Resource.error(
                            msg = "Error, data have not been returned",
                            data = null
                        )

                    } else {
                        Resource.error(
                            msg = "Fetching data error, ${response.errorBody()}",
                            data = null
                        )
                    })
            } catch (e: Exception) {
                emit(Resource.error(msg = "No data!", data = null))
            }
        }

    }

    override suspend fun getSelectedDogBreed(breedId: Int, limit: Int): Flow<Resource<SelectedBreed>> {
        return flow {
            emit(Resource.loading(data = null))

            try {
                val response = retrofitApi.getSelectedBreed(breedId,limit)
                emit(
                    if (response.isSuccessful) {
                        response.body()?.let { Resource.success(data = it) } ?: Resource.error(
                            msg = "Error, data have not been returned",
                            data = null
                        )

                    } else {
                        Resource.error(
                            msg = "Fetching data error, ${response.errorBody()}",
                            data = null
                        )
                    })
            }catch (e: Exception){
                emit(Resource.error(msg = "No data!", data = null))
            }
        }
    }
}

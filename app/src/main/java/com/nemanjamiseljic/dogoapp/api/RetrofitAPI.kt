package com.nemanjamiseljic.dogoapp.api

import com.nemanjamiseljic.dogoapp.models.breeds.Breeds
import com.nemanjamiseljic.dogoapp.models.selectedbreeds.SelectedBreed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("breeds")
    suspend fun getBreeds(): Response<Breeds>


    @GET("images/search")
    suspend fun getSelectedBreed(
        @Query("breed") breedId: Int,@Query("limit") limit: Int
    ): Response<SelectedBreed>

}
package com.nemanjamiseljic.dogoapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.nemanjamiseljic.dogoapp.R
import com.nemanjamiseljic.dogoapp.api.RetrofitAPI
import com.nemanjamiseljic.dogoapp.breeds.BreedsAdapter
import com.nemanjamiseljic.dogoapp.repo.Repository
import com.nemanjamiseljic.dogoapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }


    /**Provides application scope for coroutines**/
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.ic_launcher_foreground)
            )


    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)}

    @Provides
    fun provideRepository(retrofitAPI: RetrofitAPI) = Repository(retrofitAPI)


    @Singleton
    @Provides
    fun injectsBreedsAdapter(glide: RequestManager) = BreedsAdapter(glide = glide)



    }
package com.nemanjamiseljic.dogoapp.di

import android.app.Application
import android.content.Context
import com.nemanjamiseljic.dogoapp.repo.Repository
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@HiltAndroidApp
class DogoApplication: Application() {

}
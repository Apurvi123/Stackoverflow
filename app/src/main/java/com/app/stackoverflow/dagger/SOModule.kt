package com.app.stackoverflow.dagger

import android.app.Application
import com.app.stackoverflow.repository.Constants
import com.app.stackoverflow.repository.SORepository
import com.app.stackoverflow.repository.SORepositoryImpl
import com.app.stackoverflow.repository.localsource.SOLocalDataSource
import com.app.stackoverflow.repository.localsource.SOLocalDataSourceImpl
import com.app.stackoverflow.repository.localsource.database.SODatabase
import com.app.stackoverflow.repository.remotesource.SORemoteDataSource
import com.app.stackoverflow.repository.remotesource.SORemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SOModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): SORemoteDataSource =
        SORemoteDataSourceImpl.create(Constants.BASE_URL)

    @Provides
    @Singleton
    fun provideLocalDataSource(application: Application): SOLocalDataSource =
        SOLocalDataSourceImpl(SODatabase.getInstance(application).soDao())

    @Provides
    @Singleton
    fun provideRepository(soRemoteDataSource: SORemoteDataSource, soLocalDataSource: SOLocalDataSource): SORepository =
        SORepositoryImpl(soRemoteDataSource, soLocalDataSource)

}
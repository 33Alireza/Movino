package com.example.movino.di

import com.example.movino.api.GenresApi
import com.example.movino.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideGenresApi(client: HttpClient): GenresApi = GenresApi(client)

    @Provides
    @Singleton
    fun provideMoviesApi(client: HttpClient): MoviesApi = MoviesApi(client)
}
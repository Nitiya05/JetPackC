package com.dicoding.jetpack.di

import com.dicoding.jetpack.data.DaerahRepository

object Injection {
    fun provideRepository(): DaerahRepository {
        return DaerahRepository.getInstance()
    }
}
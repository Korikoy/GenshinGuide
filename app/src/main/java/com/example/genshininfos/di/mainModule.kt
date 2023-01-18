package com.example.genshininfos.di

import com.example.genshininfos.data.auth.AuthRepositoryImpl
import com.example.genshininfos.data.main.MainRepository
import com.example.genshininfos.domain.auth.AuthBusiness
import com.example.genshininfos.domain.main.MainBusiness
import com.example.genshininfos.network.ApiServise
import com.example.genshininfos.presentation.auth.LoginActivity
import com.example.genshininfos.presentation.auth.viewmodel.AuthViewModel
import com.example.genshininfos.presentation.main.MainActivity
import com.example.genshininfos.presentation.main.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.MainScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {
    scope<MainActivity> {
        scoped { MainBusiness(get()) }
        scoped { FirebaseAuth.getInstance() }
        scoped { provideService(get()) }
        viewModel { MainViewModel(get()) }
    }

}

fun provideService(retrofit: Retrofit):ApiServise =retrofit.create(ApiServise::class.java)
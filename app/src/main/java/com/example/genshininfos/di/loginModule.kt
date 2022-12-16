package com.example.genshininfos.di

import com.example.genshininfos.data.auth.AuthRepositoryImpl
import com.example.genshininfos.domain.auth.AuthBusiness
import com.example.genshininfos.presentation.auth.LoginActivity
import com.example.genshininfos.presentation.auth.RegisterActivity
import com.example.genshininfos.presentation.auth.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val loginModule = module {
    scope<LoginActivity> {
        scoped { AuthBusiness(get()) }
        scoped { AuthRepositoryImpl(get()) }
        scoped { FirebaseAuth.getInstance() }
        viewModel { AuthViewModel(get()) }
    }
    scope<RegisterActivity>{
        viewModel { AuthViewModel(get())}
        scoped { AuthBusiness(get()) }
        scoped { AuthRepositoryImpl(get()) }
        scoped { FirebaseAuth.getInstance() }
    }
}

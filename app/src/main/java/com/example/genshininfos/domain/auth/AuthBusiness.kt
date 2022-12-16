package com.example.genshininfos.domain.auth

import com.example.genshininfos.data.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseUser

class AuthBusiness(private val authRepositoryImpl: AuthRepositoryImpl) {
    suspend fun login(email: String, password:String) = authRepositoryImpl.login(email,password)

    suspend fun signup(name:String, email: String, password:String) = authRepositoryImpl.singup(name,email,password)

    fun logout() = authRepositoryImpl.logout()

    val currentUser: FirebaseUser?
    get() = authRepositoryImpl.currentUser
}
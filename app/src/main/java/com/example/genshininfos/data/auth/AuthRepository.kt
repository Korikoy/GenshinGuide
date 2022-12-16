package com.example.genshininfos.data.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email:String, password: String)
    suspend fun singup(name:String, email:String, password: String)
    fun logout()
}
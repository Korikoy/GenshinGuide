package com.example.genshininfos.data.auth

import com.example.genshininfos.data.utils.await
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String){
       return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun singup(name: String, email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->


        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
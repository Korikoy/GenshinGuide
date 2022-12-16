package com.example.genshininfos.presentation.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshininfos.data.auth.User
import com.example.genshininfos.di.loginModule
import com.example.genshininfos.domain.auth.AuthBusiness
import com.google.firebase.auth.*
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.concurrent.timerTask

class AuthViewModel(authBusiness: AuthBusiness): ViewModel() {
    private val authFirebase = FirebaseAuth.getInstance()

    var msgSingUp = MutableLiveData<String>()
    var resultAcaunt = MutableLiveData<Boolean>()

    var msgLogin = MutableLiveData<String>()
    var loginPass = MutableLiveData<Boolean>()
    var errorLogin = MutableLiveData<String>()
    var resetPassword = MutableLiveData<String>()
    var resetPasswordCheck = MutableLiveData<Boolean>()


    fun login(user:User){
        viewModelScope.launch {
            authFirebase.signInWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        msgLogin.postValue("Logado com sucesso!")
                        loginPass.postValue(true)
                    }else{
                        var exeption = ""
                        try {
                            throw task.exception!!
                        }catch (e: FirebaseAuthInvalidUserException){
                            loginPass.postValue(false)
                            exeption ="Essa conta nao existe ou e invalida"
                        }catch (e: FirebaseAuthInvalidCredentialsException){
                            loginPass.postValue(false)
                            exeption = "Email ou senha invalido"
                        }catch (e: Exception){
                            loginPass.postValue(false)
                            exeption = "Error: " + e.message
                            e.printStackTrace()
                        }
                        errorLogin.value = exeption
                        loginPass.postValue(false)
                    }
                }
        }

    }

    fun resetPassword(email:String){
        authFirebase.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    resetPassword.postValue("Um e-mail foi enviado para sua caixa de entrada!")
                    resetPasswordCheck.postValue(true)
                }else{
                    resetPassword.postValue("Ocorreu um problema com sua conta, contate-nos por email!")
                    resetPasswordCheck.postValue(false)
                }
            }
    }




    fun createAccaunt(user:User){
        viewModelScope.launch {
            authFirebase.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    msgSingUp.postValue("Successful Login")
                    resultAcaunt.postValue(true)
                }else{
                    var exeption = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        exeption = "Type a stronger password"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        exeption = "Type a valid email"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        exeption = "This account already exists"
                    } catch (e: Exception) {
                        exeption = "Error: " + e.message
                        e.printStackTrace()
                    }
                    msgSingUp.value = exeption
                    resultAcaunt.postValue(false)

                }

            }

            }
        }
    }


package com.example.genshininfos.presentation.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshininfos.data.main.MainRepository
import com.example.genshininfos.data.utils.FirebaseConfig
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository):ViewModel() {

}
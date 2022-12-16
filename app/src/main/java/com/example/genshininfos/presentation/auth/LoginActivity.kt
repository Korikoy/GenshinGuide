package com.example.genshininfos.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.genshininfos.data.auth.User
import com.example.genshininfos.databinding.ActivityLoginBinding
import com.example.genshininfos.presentation.auth.viewmodel.AuthViewModel
import com.example.genshininfos.presentation.main.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityLoginBinding
    private val vm by viewModel<AuthViewModel>()
    override val scope: Scope by activityScope()
    private lateinit var user: User
    private lateinit var email: EditText
    private lateinit var passwordText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var resetPassword: TextView
    private lateinit var buttonRegister: TextView
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressLogin.visibility = View.GONE
        initComponents()
        setupObservers()
        progressBar.visibility = View.GONE

    }



    private fun initComponents() {
        email = binding.editLoginEmail
        passwordText = binding.editLoginPassword
        progressBar = binding.progressLogin
        resetPassword = binding.textReset
        buttonLogin = binding.buttonJoin
        buttonRegister = binding.textRegister
    }
    private fun setupObservers() {
            resetPassword.setOnClickListener {
                resetPassword()
                vm.resetPasswordCheck.observe(this, Observer {
                    if(it == true){
                    Toast.makeText(this, vm.resetPassword.value.toString(), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, vm.resetPassword.value.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        buttonLogin.setOnClickListener {
            setupLogin()
            vm.loginPass.observe(this, Observer {
                if(it == true){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "No", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
    private fun setupLogin(){
        return if(validateField(passwordText.text.toString().trim())){
            progressBar.visibility = View.VISIBLE
            user = User(email.text.toString().trim(), passwordText.text.toString().trim())
            vm.login(user)
            vm.msgLogin.observe(this, Observer{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }else{
            vm.msgLogin.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun validateField(password: String): Boolean {
        if(email.text.toString().trim().isEmpty()){
            email.error = "Digite o email!"
            email.requestFocus()
            return false
        }
        if(password.isEmpty()){
            passwordText.error = "Digite uma senha!"
            passwordText.requestFocus()
            return false
        }
        if(!passwordMatch(password)){
            passwordText.error =
                "Sua senha precisa ter entre  8 e 16  characteres. " + "Letras maiusculas, minusculas, numeros e caractere especial."
            passwordText.requestFocus()
            return false
        }
        if(email.text.toString().trim().isEmpty() && passwordText.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Preencha todos os campos!!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun passwordMatch(password: String): Boolean {
        val default: Pattern
        val passDefault =
            "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,16}\$"
        default = Pattern.compile(passDefault)
        val matcher: Matcher = default.matcher(password)
        return matcher.matches()
    }



    private fun resetPassword() {
        if (email.text.toString().trim().isEmpty()) {
            email.error = "Insira o email no campo"
            email.requestFocus()
        } else {
            vm.resetPassword(email.text.toString())
        }
    }
}

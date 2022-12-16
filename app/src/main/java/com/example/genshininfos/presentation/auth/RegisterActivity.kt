package com.example.genshininfos.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.genshininfos.R
import com.example.genshininfos.data.auth.User
import com.example.genshininfos.databinding.ActivityLoginBinding
import com.example.genshininfos.databinding.ActivityRegisterBinding
import com.example.genshininfos.presentation.auth.viewmodel.AuthViewModel
import com.example.genshininfos.presentation.main.MainActivity
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityRegisterBinding
    private val vm by viewModel<AuthViewModel>()
    override val scope: Scope by activityScope()
    private lateinit var user : User
    private lateinit var email: EditText
    private lateinit var passwordText: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponentes()
        setupObservers()
        binding.progressBar.visibility = View.GONE

    }
    private fun initComponentes() {
        email = binding.editCadastroEmail
        passwordText = binding.editCadastroSenha
        progressBar = binding.progressBar
    }

    private fun setupObservers() {
        binding.textView.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        binding.buttonCadastrar.setOnClickListener {
            setupRegister()
            vm.resultAcaunt.observe(this, Observer {
                if(it == true){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            })
        }
    }

    private fun setupRegister(): Boolean {
        return if(validateField(passwordText.text.toString().trim())){
            progressBar.visibility = View.VISIBLE
            user = User(email.text.toString().trim(), passwordText.text.toString().trim())
            vm.createAccaunt(user)
            vm.msgSingUp.observe(this, Observer{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
            true
        }else{
            vm.msgSingUp.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
            false
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

}
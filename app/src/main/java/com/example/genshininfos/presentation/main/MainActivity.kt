package com.example.genshininfos.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.genshininfos.R
import com.example.genshininfos.data.utils.FirebaseConfig
import com.example.genshininfos.databinding.ActivityMainBinding
import com.example.genshininfos.presentation.auth.LoginActivity
import com.example.genshininfos.presentation.main.viewmodel.MainViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityMainBinding
    override val scope: Scope by activityScope()
    private val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate: MenuInflater = menuInflater
        inflate.inflate(R.menu.home_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menuConfig -> {
                Toast.makeText(this, "Function not yet implemented", Toast.LENGTH_LONG).show()
            }

            R.id.logout -> {
                logout()
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }

            else -> Toast.makeText(applicationContext, "Nothing here to see", Toast.LENGTH_SHORT)
                .show()
        }
        return super.onOptionsItemSelected(item)

}
    fun toolbar() {
        setSupportActionBar(binding.toolbarID.toolbar)
        supportActionBar?.title = "Characters"
    }
    fun logout(){
        val fire = FirebaseConfig().fireInstance()
        fire.signOut()
    }
}


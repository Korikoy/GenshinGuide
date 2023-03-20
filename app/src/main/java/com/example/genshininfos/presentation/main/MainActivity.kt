package com.example.genshininfos.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.genshininfos.R
import com.example.genshininfos.data.utils.FirebaseConfig
import com.example.genshininfos.databinding.ActivityMainBinding
import com.example.genshininfos.presentation.auth.LoginActivity
import com.example.genshininfos.presentation.main.adapter.CustomAdapter
import com.example.genshininfos.presentation.main.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL

class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityMainBinding
    lateinit var resultList: ArrayList<String>
    lateinit var adapter: CustomAdapter

    override val scope: Scope by activityScope()
    private val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar()

        callApi(this)

    }

    fun callApi(context: Context) {
        val client = OkHttpClient().newBuilder().build()
        var result: String? = null
        try {
            val url = URL("https://api.genshin.dev/characters")
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    result = response.body?.string()
                    val type: Type = object : TypeToken<ArrayList<String>>() {}.type
                    resultList = Gson().fromJson(result, type)
                    adapter(resultList, this@MainActivity)

                }

            })

        } catch (err: Error) {
            print("Error when executing get requeriment " + err.localizedMessage)

        }

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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Characters"
    }

    fun logout() {
        val fire = FirebaseConfig().fireInstance()
        fire.signOut()
    }

    fun adapter(lista: ArrayList<String>, context: Context) {

        runOnUiThread {
            adapter = CustomAdapter(resultList, this@MainActivity)
            binding.recicleView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            binding.recicleView.adapter = adapter
        }

    }


}
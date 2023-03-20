package com.example.genshininfos.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.genshininfos.databinding.ActivityCharacterBinding
import com.example.genshininfos.databinding.ActivityCharacterBinding.inflate
import com.example.genshininfos.databinding.ActivityMainBinding
import com.example.genshininfos.presentation.main.adapter.CustomAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL

class CharacterActivity : AppCompatActivity() {

    lateinit var characterSelected : String
    lateinit var binding: ActivityCharacterBinding
    lateinit var nameExib:String
    lateinit var characterInfo: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if(bundle != null){
            characterSelected = bundle.getSerializable("character") as String
            val character : String = characterSelected
            val persona = character.replace("-"," ")
            nameExib = persona.capitalizeWords()

        }
        toolbar()
        atualizeCharacterImage()
        callApi(this,characterSelected)



    }
    fun toolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = nameExib
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
    fun callApi(context: Context, character: String) {
        val client = OkHttpClient().newBuilder().build()
        var result: String? = null
        try {
            val url = URL("https://api.genshin.dev/characters/$character")
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    result = response.body?.string()
                    val type: Type = object : TypeToken<Character>() {}.type
                    characterInfo = Gson().fromJson(result, type)
                    atualizeCharacter()
                    println(characterInfo.affiliation)



                }

            })

        } catch (err: Error) {
            print("Error when executing get requeriment " + err.localizedMessage)

        }

    }
    @SuppressLint("SetTextI18n")
    fun atualizeCharacter(){
        runOnUiThread {
            binding.textTitle.text = "Title: ${characterInfo.title}"
            binding.textConste.text = "Constelaton: ${characterInfo.constellation}"
            binding.textDescrip.text = characterInfo.description
            binding.textNation.text = "Nation: ${characterInfo.nation}"
            binding.textRarity.text = "Rarity: ${characterInfo.rarity}"
            binding.textVision.text = "Vision: ${characterInfo.vision}"
            binding.textWeapon.text = "Weapon: ${characterInfo.weapon}"
            binding.textTalent0.text = " Name: ${characterInfo.passiveTalents[0].name} \n Unlock: ${characterInfo.passiveTalents[0].unlock} \n Description: ${characterInfo.passiveTalents[0].description} "
            binding.textTalent2.text = " Name: ${characterInfo.passiveTalents[1].name} \n Unlock: ${characterInfo.passiveTalents[1].unlock} \n Description: ${characterInfo.passiveTalents[1].description} "
           if(characterSelected.contains("traveler")){
               binding.textTalent3.visibility = View.GONE
               binding.imageTalent3.visibility = View.GONE

           }else {
               binding.textTalent3.text = " Name: ${characterInfo.passiveTalents[2].name} \n Unlock: ${characterInfo.passiveTalents[2].unlock} \n Description: ${characterInfo.passiveTalents[2].description} "
           }
        }
    }

    fun atualizeCharacterImage(){
            val image = "https://api.genshin.dev/characters/$characterSelected/portrait"
            Picasso.get().load(image).into(binding.imageTest)
            val image1 = "https://api.genshin.dev/characters/$characterSelected/talent-passive-0"
            Picasso.get().load(image1).into(binding.imageTalent3)
            val image2 = "https://api.genshin.dev/characters/$characterSelected/talent-passive-1"
            Picasso.get().load(image2).into(binding.imageTalent0)
            val image3 = "https://api.genshin.dev/characters/$characterSelected/talent-passive-2"
            Picasso.get().load(image3).into(binding.imageTalent2)


    }
}
package com.example.genshininfos.presentation.main.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.genshininfos.R
import com.example.genshininfos.databinding.ActivityMainBinding
import com.example.genshininfos.databinding.CardLayoutBinding
import com.squareup.picasso.Picasso

class CustomAdapter(modelList: List<String>, var contex: Context):RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    private val characters = modelList

    inner class
    MyViewHolder(val binding: CardLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                val position:Int = adapterPosition
                val charapter : String = characters[position]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.MyViewHolder {
        contex = parent.context
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(contex),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {
        val character : String = characters[position]
        holder.binding.name.text = character
        val image = "https://api.genshin.dev/characters/$character/icon"
        Picasso.get().load(image).into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return characters.size
    }


}
package com.example.userapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userapi.databinding.ItemRvBinding
import com.example.userapi.models.User

class UserAdapter (val list: List<User>) : RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemRv : ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root){
        fun onBind(user: User, position: Int){
            itemRv.rvName.text = user.login
            Glide.with(itemRv.root).load(user.avatar_url).into(itemRv.rvImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}
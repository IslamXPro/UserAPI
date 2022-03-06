package com.android.booksearchrxjavakotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.booksearchrxjavakotlin.network.VolumeInfo
import com.bumptech.glide.Glide
import com.example.userapi.R
import kotlinx.android.synthetic.main.item_rv.view.*

class BookListAdapter: RecyclerView.Adapter<BookListAdapter.MyViewHolder>() {

    var bookListData = ArrayList<VolumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.MyViewHolder {
       val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false )
        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: BookListAdapter.MyViewHolder, position: Int) {
            holder.bind(bookListData[position])
    }

    override fun getItemCount(): Int {
        return bookListData.size
    }

    class   MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvTitle = view.rv_name
        val thumbImageView = view.rv_img

        fun bind(data : VolumeInfo){
            tvTitle.text = data.volumeInfo.title

            val url  = data .volumeInfo.imageLinks.smallThumbnail
            Glide.with(thumbImageView)
                .load(url)
                .circleCrop()
                .into(thumbImageView)
        }
    }
}
package com.kimminsu.lf.view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimminsu.lf.databinding.ImageRecyclerviewItemBinding

class MultiImageAdapter: RecyclerView.Adapter<MultiImageAdapter.imageHolder>() {
    private lateinit var context: Context
    private var listData = mutableListOf<Uri>()

    inner class imageHolder(private val binding: ImageRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Uri){
            binding.image.setImageURI(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageHolder {
        val imageItemBinding = ImageRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("oncreate", "oncreate")
        return imageHolder(imageItemBinding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: imageHolder, position: Int) {
        val uri = listData[position]
        holder.onBind(uri)
    }

    fun setDataList(dataList: List<Uri>){
        this.listData = dataList as MutableList<Uri>
        Log.d("change", "change")
        notifyDataSetChanged()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUri: Uri){
        Log.d("uri", "$imageUri")
        Glide.with(view.context)
            .load(imageUri)
            .fitCenter()
            .into(view)
    }
}
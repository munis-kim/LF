package com.kimminsu.lf.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.databinding.PostRecyclerviewItemBinding

class PostAdapter(var data: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    private var postList = data
    inner class MyViewHolder(val binding: PostRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            Log.d("adapterPostList", "$postList")
            Log.d("adapterPost", "$post")
            binding.createdAt.text = post.uploadTime
            binding.title.text = post.title

            Glide.with(binding.tumbnailImage)
                .load(post.image)
                .into(binding.tumbnailImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PostRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
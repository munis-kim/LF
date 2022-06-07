package com.kimminsu.lf.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kimminsu.lf.data.RecyclerViewMap
import com.kimminsu.lf.databinding.MapRecyclerviewItemBinding

class MapAdapter: RecyclerView.Adapter<MapAdapter.ViewHolder>() {
    var data = listOf<RecyclerViewMap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MapRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding: MapRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RecyclerViewMap){
            binding.map = data
        }
    }
}
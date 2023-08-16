package com.example.videoplayer

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videoplayer.databinding.RvLayoutBinding

class videoadapter(private val context:Context, private val videolist: ArrayList<videos>):RecyclerView.Adapter<videoadapter.viewHolder>() {
    class viewHolder(binding:RvLayoutBinding):RecyclerView.ViewHolder(binding.root) {
       val txt = binding.videoNameText
        val foldername = binding.folderNameText
        val duration  = binding.durationText
        val img = binding.rvImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
    return viewHolder(RvLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {

        return videolist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.txt.text = videolist[position].tittle
        holder.foldername.text = videolist[position].foldername
        holder.duration.text = DateUtils.formatElapsedTime(videolist[position].dur/1000)
         Glide.with(context)
             .asBitmap()
             .load(videolist[position].artUri)
             .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
             .into(holder.img)
    }
}
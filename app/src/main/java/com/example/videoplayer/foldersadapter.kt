package com.example.videoplayer

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videoplayer.databinding.FolderRvBinding
import com.example.videoplayer.databinding.RvLayoutBinding

class foldersadapter(private val context:Context, private val folderlist: ArrayList<folder>):RecyclerView.Adapter<foldersadapter.viewHolder>() {
    class viewHolder(binding: FolderRvBinding) : RecyclerView.ViewHolder(binding.root) {
        val foldername = binding.folderNameRv
        val root = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(FolderRvBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {

        return folderlist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.foldername.text = folderlist[position].foldername
        holder.root.setOnClickListener {
            val intent = Intent(context, folderinsidevideos::class.java)
            intent.putExtra("position", position)
            ContextCompat.startActivity(context, intent, null)

        }


    }
}
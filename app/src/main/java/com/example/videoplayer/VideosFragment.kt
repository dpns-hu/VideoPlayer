package com.example.videoplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.databinding.FragmentVideosBinding


class VideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

       val view = inflater.inflate(R.layout.fragment_videos, container, false)
        val binding = FragmentVideosBinding.bind(view)
        binding.videorv.setHasFixedSize(true)
        binding.videorv.setItemViewCacheSize(10)
        binding.videorv.layoutManager = LinearLayoutManager(requireContext())
        binding.videorv.adapter = videoadapter(requireContext(),MainActivity.videoslist)
        binding.totalVideos.text = "Total Videos ${MainActivity.videoslist.size}"
        return view
            //commit
    }


}
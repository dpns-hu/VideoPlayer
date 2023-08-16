package com.example.videoplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.databinding.FragmentFolderBinding
import com.example.videoplayer.databinding.FragmentVideosBinding


class FolderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_folder, container, false)
        val binding = FragmentFolderBinding.bind(view)





        binding.folderSetRv.setHasFixedSize(true)
        binding.folderSetRv.setItemViewCacheSize(10)
        binding.folderSetRv.layoutManager = LinearLayoutManager(requireContext())
        binding.folderSetRv.adapter = foldersadapter(requireContext(),MainActivity.folderlist)
       binding.totalFolder.text = "Total Folder ${MainActivity.folderlist.size}"
        return view

    }


}
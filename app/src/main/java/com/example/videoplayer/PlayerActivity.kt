package com.example.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.videoplayer.MainActivity.Companion.videoslist
import com.example.videoplayer.databinding.ActivityPlayerBinding
import com.example.videoplayer.folderinsidevideos.Companion.foldervideoslist
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.util.SimpleTimeZone

class PlayerActivity : AppCompatActivity() {
    companion object{
        var playerlist = ArrayList<videos>()
        var position :Int = -1;
        lateinit var player :SimpleExoPlayer
    }
    lateinit var binding:ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          binding = ActivityPlayerBinding.inflate(layoutInflater)

        setContentView(binding.root)

       layout()
        initializeBinding()

    }
    private fun layout(){
        when(intent.getStringExtra("class")){
            "FolderActivity"->{
                playerlist = ArrayList<videos>()
                playerlist.addAll(foldervideoslist)
            }
            "AllVideos"-> {
                playerlist = ArrayList<videos>()
                playerlist.addAll(videoslist)
            }
        }
        createPlayer()
    }
    private fun initializeBinding(){
        binding.backBtn.setOnClickListener{
            finish()
        }
    }
    private fun createPlayer(){
        binding.videoTitile.text = playerlist[position].tittle
        binding.videoTitile.isSelected = true
        player= SimpleExoPlayer.Builder(this ).build()
        binding.playerView.player = player
        val mediaItems =    MediaItem.fromUri(playerlist[position].artUri)
        player.setMediaItem(mediaItems)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
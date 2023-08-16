package com.example.videoplayer

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.databinding.ActivityFolderinsidevideosBinding
import java.io.File
import java.lang.Exception

class folderinsidevideos : AppCompatActivity() {
    companion object{
        var foldervideoslist= ArrayList<videos>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFolderinsidevideosBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_VideoPlayer)
        setContentView(binding.root)
        val position = intent.getIntExtra("position",0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title= MainActivity.folderlist[position].foldername
        foldervideoslist= getTotalV(MainActivity.folderlist[position].id)
        binding.folderInsideRv.setHasFixedSize(true)
        binding.folderInsideRv
            .setItemViewCacheSize(10)
        binding.folderInsideRv.layoutManager = LinearLayoutManager(this)
        binding.folderInsideRv.adapter = videoadapter(this, foldervideoslist,true)
        binding.totalVideosFolder.text = "Total Videos ${foldervideoslist.size}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
       return true
    }
    @SuppressLint("Range", "SuspiciousIndentation")
    private fun getTotalV(FolderId: String) : ArrayList<videos>{
        val videoslist = ArrayList<videos>()
         val selection = MediaStore.Video.Media.BUCKET_ID+ " like?"
        val projection =    arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME
            ,
            MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.BUCKET_ID)
        val cursor = this.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,selection,
            arrayOf(FolderId), MediaStore.Video.Media.DATE_ADDED+" DESC")
        if(cursor!=null){
            if(cursor.moveToNext()){
                do{
                    val tittleC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val Idc = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderNameC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val folderIdC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
                    val DurC = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()
                    val DataC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    try {
                        val file = File(DataC)
                        val ArturiC = Uri.fromFile(file)
                        val vid = videos(tittle = tittleC, id = Idc, foldername = folderNameC, dur = DurC, size = sizeC, path = DataC, artUri = ArturiC )
                        if(file.exists())videoslist.add(vid)


                    }catch (e: Exception){}
                }while(cursor.moveToNext())
                cursor?.close()
            }
        }
        return videoslist;
    }
}
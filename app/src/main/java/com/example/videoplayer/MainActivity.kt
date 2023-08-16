package com.example.videoplayer

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.CaseMap.Fold
import android.icu.text.CaseMap.fold
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video
import android.provider.MediaStore.Video.Media
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.videoplayer.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    companion object    {
        var videoslist = ArrayList<videos>()
        var  folderlist = ArrayList<folder>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this,binding.drawerid,R.string.open,R.string.close)
        binding.drawerid.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(requestPermission()){
            folderlist = ArrayList()
            videoslist= getTotalV()
            fragmentsetter(VideosFragment())
        }



        binding.navigationBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.folder_menu -> fragmentsetter(FolderFragment())
                R.id.videos_menu -> fragmentsetter(VideosFragment())

            }
            return@setOnItemSelectedListener true
        }
        binding.navigationview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.exitt -> exitProcess(1)
                R.id.about_menu -> exitProcess(1)
                R.id.themes_menu -> exitProcess(1)
                R.id.Feedback    -> exitProcess(1)
            }
            return@setNavigationItemSelectedListener true
        }


    }

    private fun fragmentsetter(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Framelayout,fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
    private fun requestPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),10)
            return false;
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==10){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"grangted",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),10)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))return true
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("Range", "SuspiciousIndentation")
    private fun getTotalV() : ArrayList<videos>{
        val videoslist = ArrayList<videos>()
        val tempfolderlist = ArrayList<String>()
        val projection =    arrayOf(MediaStore.Video.Media.TITLE,MediaStore.Video.Media.SIZE,MediaStore.Video.Media._ID,MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        ,MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION,MediaStore.Video.Media.DATE_ADDED,MediaStore.Video.Media.BUCKET_ID)
        val cursor = this.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null, MediaStore.Video.Media.DATE_ADDED+" DESC")
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
                           if(!tempfolderlist.contains(folderIdC)){
                                tempfolderlist.add(folderIdC)
                               folderlist.add(folder(id = folderIdC, foldername = folderNameC))
                           }
                       }catch (e:Exception){}
                   }while(cursor.moveToNext())
                   cursor?.close()
               }
           }
        return videoslist;
    }
}
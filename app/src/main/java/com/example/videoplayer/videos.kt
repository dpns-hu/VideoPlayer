package com.example.videoplayer

import android.net.Uri

data class videos(val id:String,val tittle:String, val dur:Long=0 ,val foldername: String,val size:String,val path:String
, val artUri:Uri)

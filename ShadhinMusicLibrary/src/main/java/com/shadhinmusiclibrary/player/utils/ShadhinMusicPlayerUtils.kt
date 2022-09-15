package com.gm.shadhin.player.utils

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_CHANNEL_ID
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_DESCRIPTION
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_NAME
import com.gm.shadhin.player.data.model.MusicPlayList
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable


fun createMusicNotificationChannel(context: Context) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MUSIC_NOTIFICATION_CHANNEL_ID,
                MUSIC_NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = MUSIC_NOTIFICATION_DESCRIPTION
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
 }
 fun isServiceRunning(context: Context,serviceName: String): Boolean {
    var serviceRunning = false
    val am: ActivityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    val l: List<ActivityManager.RunningServiceInfo> = am.getRunningServices(50)
    val i: Iterator<ActivityManager.RunningServiceInfo> = l.iterator()
    while (i.hasNext()) {
        val runningServiceInfo: ActivityManager.RunningServiceInfo = i
            .next()

        if (runningServiceInfo.foreground) {
            Log.i("ShadhinMusicPlayer", "isServiceRunning: ${runningServiceInfo.service.className}")
            //service run in foreground
        }
        /*if (runningServiceInfo.service.getClassName().equals(serviceName)) {
            serviceRunning = true
            if (runningServiceInfo.foreground) {
                Log.i("ShadhinMusicPlayer", "isServiceRunning: ${runningServiceInfo.toString()}")
                //service run in foreground
            }
        }*/
    }
    return serviceRunning
}
fun bitmapFromUri(context: Context, imageUrl: Uri?, onBitmap: (image: Bitmap?) -> Unit) {
    imageUrl?.let { uri->
        Glide.with(context).asBitmap()
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                  //  Log.i("DataSourceFactory", "onResourceReady: ${Thread.currentThread().name}")
                    onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }


                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    onBitmap(null)
                }
            })
    }

}
fun bitmapFromUri(context: Context, imageUrl: Uri?, size: Int, onBitmap: (image: Bitmap?) -> Unit) {
    imageUrl?.let { uri->
        Glide.with(context).asBitmap()
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>(size, size) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }


                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    onBitmap(null)
                }
            })
    }

}
fun bitmapFromUriS(context: Context, imageUrl: Uri?, onBitmap: (Bitmap) -> Unit) {
    imageUrl?.let { uri->
        Glide.with(context).asBitmap()
            .load(uri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

}
fun String?.nullFix(): String {
    if(this !=null && this != "null"){
        return this
    }
    return ""
}
fun Int?.nullFix(): String {
    if(this ==null){
        return "0"
    }
    return this.toString()
}
fun CharSequence?.nullFix(): CharSequence {
    if(this !=null && this != "null"){
        return this
    }
    return ""
}
fun delay(time: Long, callBack: () -> Unit){
    Handler(Looper.getMainLooper()).postDelayed(Runnable {
        callBack()
    }, time)
}
fun Serializable.sizeInByte(): Int {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(this)
    oos.close()
    return baos.size()
}

//copy from DownloadService
fun isConnectedToInternet(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return isConnected
}


private val bitmapHasMap:HashMap<String,Bitmap?> = HashMap()
fun preloadBitmapClear(){
    bitmapHasMap.clear()
}
fun preLoadBitmap(playlist: MusicPlayList, context: Context){

    playlist.list.forEach { music ->
        bitmapFromUri(context, Uri.parse(music.displayIconUrl),200){
            bitmapHasMap[music.mediaId.toString()] = it
        }
    }
}
fun getPreloadBitmap(mediaId:String):Bitmap?{
    return bitmapHasMap[mediaId.toString()]
}





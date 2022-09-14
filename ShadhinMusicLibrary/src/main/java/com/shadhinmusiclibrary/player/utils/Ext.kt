//package com.shadhinmusiclibrary.player.utils
//
//import android.content.Context
//import android.content.res.Resources
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.provider.Settings
//import android.text.Html
//import android.util.Log
//import android.util.TypedValue
//import android.view.View
//import android.view.ViewGroup
//import android.view.inputmethod.InputMethodManager
//import android.widget.Button
//import android.widget.TextView
//import androidx.annotation.ColorInt
//import androidx.annotation.RawRes
//import androidx.annotation.RequiresApi
//import androidx.core.net.toUri
//import androidx.core.text.HtmlCompat
//import com.gm.shadhin.BuildConfig
//import com.gm.shadhin.data.model.CategoryContents
//import com.gm.shadhin.data.model.internal_ads.InternalAdsResponse
//import com.gm.shadhin.data.model.podcast.PodcastExplore
//import com.gm.shadhin.data.model.subscription.CelcomSubscriptionResponse
//import com.gm.shadhin.data.storage.LocalStorage
//import com.gm.shadhin.data.storage.db.download.OfflineDownload
//import com.gm.shadhin.player.data.model.Music
//import com.shadhinmusiclibrary.player.data.source.ShadhinDataSourceFactory
//import com.shadhinmusiclibrary.player.utils.makeValidMp4Url
//import com.shadhinmusiclibrary.player.utils.toMusic
//import com.gm.shadhin.util.Constants.FILE_BASE_URL
//import com.gm.shadhin.util.fuska.BfcUtils
//import com.gm.shadhin.util.fuska.BrainFuska
//import com.gm.shadhin.util.fuska.createSource
//import com.gm.shadhin.util.fuska.decryptData
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.MediaMetadata
//import com.google.android.exoplayer2.source.ProgressiveMediaSource
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.perf.ktx.performance
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.Serializable
//import java.lang.reflect.Modifier
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.concurrent.TimeUnit
//import java.util.regex.Pattern
//import kotlin.reflect.full.declaredMemberProperties
//import kotlin.reflect.full.primaryConstructor
//import kotlin.system.measureTimeMillis
//
///**
// * @AUTHOR: Mehedi Hasan
// * @DATE: 2/25/2021, Thu
// */
//
//fun View.hide() {
//    this.visibility = View.GONE
//}
//fun tests(block: String.() -> Unit){
//
//}
//
//fun View.showKeyboard() {
//    post {
//        if (this.requestFocus()) {
//            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }
//}
//val ukey2 = "STEHLGCilw_Y9_11qcW8"
//
//fun View.hideKeyboard() {
//    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputManager.hideSoftInputFromWindow(windowToken, 0)
//}
//
//fun Int.toDp(context: Context): Int {
//    val scale = context.resources.displayMetrics.density
//    return (16.0f * scale + 0.5f).toInt()
//}
//fun String.preContentType(): String? {
//   return when(this.length){
//        4 -> this.substring(0..1)
//        else -> null
//    }
//}
//fun String.postContentType(): String?{
//    return when(this.length){
//        4 -> this.substring(2..3)
//        else -> null
//    }
//}
///*fun main(){
//    "VDKC".preContentType().also(::println)
//    "VDKC".postContentType().also(::println)
//}*/
//val Int.dp: Int
//    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
//val Int.px: Int
//    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
//val ukey = "AIzaSyAO_FJ2SlqU8Q4"
//
//val ykeyTotal = ukey + ukey2
//
//fun OfflineDownload.copy(
//        contentId: String? = null,
//        rootId: String? = null,
//        rootImg: String? = null,
//        rootTitle: String? = null,
//        rootType: String? = null,
//        track: CategoryContents.Data? = null,
//        type: Int? = null,
//        isDownloaded: Int? = null,
//        isFavorite: Int? = null,
//        timeStamp: Long? = null,
//        isCheck: Boolean? = null,
//        isDownloading: Boolean? = null,
//        isHistory: Boolean? = null,
//        isPlaying: Boolean? = null,
//): OfflineDownload {
//
//    val obj = OfflineDownload()
//    obj.timeStamp = this.timeStamp
//    obj.id = this.id
//    obj.contentId = this.contentId
//    obj.rootId = this.rootId
//    obj.rootImg = this.rootImg
//    obj.rootTitle = this.rootTitle
//    obj.rootType = this.rootType
//    obj.track = this.track
//    obj.type = this.type
//    obj.isDownloaded = this.isDownloaded
//    obj.isFavorite = this.isFavorite
//    obj.timeStamp = this.timeStamp
//    obj.isCheck = this.isCheck
//    obj.isDownloading = this.isDownloading
//    obj.isHistory = this.isHistory
//    obj.isPlaying = this.isPlaying
//
//    if (contentId != null) {
//        obj.contentId = contentId
//    }
//    if (rootId != null) {
//        obj.rootId = rootId
//    }
//    if (rootImg != null) {
//        obj.rootImg = rootImg
//    }
//    if (rootTitle != null) {
//        obj.rootTitle = rootTitle
//    }
//    if (rootType != null) {
//        obj.rootType = rootType
//    }
//    if (track != null) {
//        obj.track = track
//    }
//    if (type != null) {
//        obj.type = type
//    }
//    if (isDownloaded != null) {
//        obj.isDownloaded = isDownloaded
//    }
//    if (isFavorite != null) {
//        obj.isFavorite = isFavorite
//    }
//    if (timeStamp != null) {
//        obj.timeStamp = timeStamp
//    }
//    if (isCheck != null) {
//        obj.isCheck = isCheck
//    }
//    if (isDownloading != null) {
//        obj.isDownloading = isDownloading
//    }
//    if (isHistory != null) {
//        obj.isHistory = isHistory
//    }
//    if (isPlaying != null) {
//        obj.isPlaying = isPlaying
//    }
//    return obj
//}
//
//fun createTimeLabel(second: Long): String? {
//    var timeLabel: String? = ""
//    val min = second / 60
//    val sec = second % 60
//    timeLabel += "$min:"
//    if (sec < 10) timeLabel += "0"
//    timeLabel += sec
//    return timeLabel
//}
//fun randomString(length: Int) : String {
//    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
//    return (1..length)
//        .map { allowedChars.random() }
//        .joinToString("")
//}
//
//fun delay(millis: Long, callback: () -> Unit) {
//    Handler(Looper.getMainLooper()).postDelayed(Runnable {
//        callback.invoke()
//    }, millis)
//}
//
//fun List<OfflineDownload>.filterByName(text: String): List<OfflineDownload> {
//    return this.filter { offlineDownload ->
//        !offlineDownload.isDownloading &&
//                offlineDownload.track.title.uppercase().contains(text.uppercase())
//    }
//}
//
//fun List<OfflineDownload>.toCategoryData(): List<CategoryContents.Data> {
//    return this.map { it.track }
//}
//
//fun List<OfflineDownload>.newList(isCheck: Boolean? = null): MutableList<OfflineDownload> {
//    val newList: MutableList<OfflineDownload> = ArrayList()
//    this.forEach {
//        newList.add(it.copy(isCheck = isCheck))
//    }
//    return newList
//}
//
//fun List<CategoryContents.Data>.toOfflineDownloadObjectsList(isDownloading: Boolean? = false, isHistory: Boolean? = false): List<OfflineDownload> {
//    val newList: MutableList<OfflineDownload> = ArrayList()
//    this.forEach { data ->
//        val obj = OfflineDownload().copy(
//                contentId = data.contentID,
//                rootId = data.rootId,
//                rootImg = data.rootImage,
//                rootTitle = data.rootTitle,
//                rootType = data.rootType,
//                track = data,
//                timeStamp = data.timeStamp,
//                isDownloading = isDownloading,
//                isHistory = isHistory
//        )
//        newList.add(obj)
//    }
//    return newList
//}
//
//fun List<OfflineDownload>.createNewDownloadItem(isPlaying: Boolean? = false): MutableList<OfflineDownload> {
//    val newList: MutableList<OfflineDownload> = ArrayList()
//    this.forEach { data ->
//        val newItem = data.copy(
//                isPlaying = isPlaying
//        )
//        newList.add(newItem)
//    }
//    return newList
//}
//
//fun List<OfflineDownload>.addDownloadingItem(downloading: List<OfflineDownload>): MutableList<OfflineDownload> {
//    val newList = this.toMutableList()
//    downloading.forEach {
//        val index = newList.indexOfFirst { offlineDownload -> offlineDownload.track.contentID == it.track.contentID }
//        if (index != -1) {
//            newList[index] = it.copy()
//        } else {
//            newList.add(0, it)
//        }
//    }
//    return newList
//}
//
//fun List<OfflineDownload>?.remove(removedItems: List<OfflineDownload>?): List<OfflineDownload>? {
//
//    val newList = this?.filter { od ->
//        val index = removedItems?.indexOfFirst { od2 ->
//            Log.i("PLISTCNTXC", "remove: ${od2.track.contentID}  ${od.track.contentID}")
//            od2.track.contentID == od.track.contentID
//        }
//
//        index == -1
//    }
//    return newList
//
//}
//
//fun List<OfflineDownload>?.removeDownloading(): List<OfflineDownload>? {
//
//    val mutableList: MutableList<OfflineDownload> = ArrayList()
//
//    this?.forEach { offlineDownload ->
//        mutableList.add(offlineDownload.copy(isDownloading = false))
//    }
//    return mutableList
//
//}
//
//fun getProgressPercent(totalCount: Int, downloadedCount: Int): Double {
//    try {
//        return downloadedCount.toDouble() / totalCount.toDouble() * (100.00).toDouble()
//    } catch (e: Exception) {
//        Log.e("getProgressPercentErr", e.localizedMessage)
//    }
//    return 0.1
//}
//
//
//@ColorInt
//fun Context.getColorFromAttr(resid: Int): Int {
//    val typedValue = TypedValue()
//    val theme = this.theme
//    theme.resolveAttribute(resid, typedValue, true)
//    return typedValue.data
//}
//
//fun TextView.setTextColorFromAttr(resid: Int){
//    val color = this.context.getColorFromAttr(resid)
//    this.setTextColor(color)
//}
//fun Button.setTextColorFromAttr(resid: Int){
//    val color = this.context.getColorFromAttr(resid)
//    this.setTextColor(color)
//}
//fun TextView.textColor(resid: Int) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        this.setTextColor(this.context.getColor(resid))
//    } else {
//        this.setTextColor(this.context.resources.getColor(resid))
//    }
//}
//fun Button.textColor(resid: Int) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        this.setTextColor(this.context.getColor(resid))
//    } else {
//        this.setTextColor(this.context.resources.getColor(resid))
//    }
//}
//
//fun List<OfflineDownload>.ids(): List<String> {
//
//    return this.map { it.track.contentID }
//}
//
//fun List<OfflineDownload>.rootIds(): List<String> {
//    return this.map { it.rootId }
//}
//
//fun List<OfflineDownload>.isPlaying(mediaId: String): Boolean {
//    return this.ids().contains(mediaId)
//}
//
//fun isValidBangladeshiPhoneNumber(phoneNumber:String?):Boolean{
//    return phoneNumber?.let { Regex( "\\+?(88)?01[13456789]\\d{2}\\-?\\d{6}").matches(it) }?:false
//}
///**https://en.wikipedia.org/wiki/Telephone_numbers_in_Bangladesh*/
//fun isBangladeshiPhoneNumber(phoneNumber: String?): Boolean {
//
//    return phoneNumber?.let { Regex("\\+?(88)01[13456789]\\d{2}\\-?\\d{6}").matches(it) }?:false
//}
//fun isBanglalink(phoneNumber:String) =  Regex( "\\+?(88)?01[49]\\d{8}").matches(phoneNumber)
//fun isRobiOrArtel(phoneNumber:String) =  Regex( "\\+?(88)?01[68]\\d{8}").matches(phoneNumber)
//
//fun isGP(phoneNumber:String) =  Regex( "\\+?(88)?01[37]\\d{8}").matches(phoneNumber)
//
//fun String.removePlus() = this.replace("+","")
//
//fun main(){
//    println(
//        isValidBangladeshiPhoneNumber("8802776402271")
//    )
//    println(
//        isBangladeshiPhoneNumber("8802776402271")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("+8801212345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("+8801912345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("8801912345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("8801412345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("+8801812345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("01412345678")
//    )
//    println(
//        isValidBangladeshiPhoneNumber("+8801476402271")
//    )
//}
//
//fun String.convert11DigitBdNumber(): String? {
//   val regex = Regex("01[13456789]\\d{2}\\-?\\d{6}")
//   return regex.find(this)?.groupValues?.first()
//}
//fun String.parseFloatNumber(): Float? {
//    return exH { Regex("[+-]?([0-9]*[.])?[0-9]+")
//        .find(this)
//        ?.groupValues
//        ?.first()
//        ?.toFloat()
//    }
//}
//
//
//
//fun rangeMap(x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
//    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
//}
//fun calculateVideoHeight(displayWidth:Int,videoWidth:Int,videoHeight:Int): Int {
//    return rangeMap(videoHeight.toFloat(),0F,videoWidth.toFloat(),0F,displayWidth.toFloat()).toInt()
//}
//fun calculateVideoWidth(displayHeight:Int, videoHeight:Int, videoWidth:Int): Int {
//    return rangeMap(videoWidth.toFloat(),0F,videoHeight.toFloat(),0F,displayHeight.toFloat()).toInt()
//}
//
//
//inline fun<T> exH(func:()->T):T?{
//    return try {
//        func.invoke()
//    }catch (e:Exception){
//        null
//    }
//}
//fun <T> CoroutineScope.asyncCallback(func:suspend ()->T, callbackFunc:(T)->Unit){
//    this.launch {
//        val rObj:T = func.invoke()
//        withContext(Dispatchers.Main){
//            callbackFunc.invoke(rObj)
//        }
//    }
//}
//fun <T>CoroutineScope.processWithCoroutine(inputFunc:()->T,callbackFunc:(T)->Unit){
//    this.launch {
//        val rObj:T = inputFunc.invoke()
//        withContext(Dispatchers.Main){
//            callbackFunc.invoke(rObj)
//        }
//    }
//}
//
//fun String.makeFullContentUrl(): String {
//    return if(!this.isFullContentUrl()){
//        FILE_BASE_URL + this
//    }else{
//       this
//    }
//}
//fun String.makePathContentUrl(): String {
//    return if(this.isFullContentUrl()){
//        this.replace(FILE_BASE_URL,"")
//    }else{
//        this
//    }
//}
//fun String.isFullContentUrl(): Boolean {
//    return this.contains(FILE_BASE_URL,true)
//}
//inline fun<T> printExeTime(tag:String, message:String?=null, func:()->T):T{
//
//    var fc:T?=null
//    val time = measureTimeMillis {
//        fc =  func.invoke()
//    }
//    if(BuildConfig.DEBUG) {
//        Log.i(tag, "$message: $time ms")
//    }
//    return fc!!
//}
//inline fun<T> monitorPerformance(tag:String, identity:String, func:()->T):T{
//    val trace = Firebase.performance.newTrace(tag)
//    trace.start()
//    var fc:T?=null
//    val time = measureTimeMillis {
//        fc =  func.invoke()
//    }
//    if(BuildConfig.DEBUG) {
//        Log.i(tag, "$identity: $time ms")
//    }
//    trace.stop()
//    return fc!!
//}
//
//
//inline fun<T> debug(func:()->T):T? = if(BuildConfig.DEBUG){func.invoke() }else{ null }
//
//fun TextView.htmlText(res: Int) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        this.text =
//            Html.fromHtml(this.resources.getString(res), HtmlCompat.FROM_HTML_MODE_LEGACY)
//    }else{
//        this.text =
//            Html.fromHtml(this.resources.getString(res))
//    }
//}
//fun TextView.htmlText(text: String) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        this.text =
//            Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
//    }else{
//        this.text =
//            Html.fromHtml(text)
//    }
//}
//
//fun Date.toTimeFormat(): String {
//    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(this)
//}
//fun String.toDate():Date?{
//   return exH { SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.getDefault()).parse(this) }
//}
//fun Date.normalize(): String {
//    return SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.getDefault()).format(this)
//}
//
//operator fun Date.minus(month:Month): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MONTH, - month.value)
//    return  calendar.time
//}
//operator fun Date.minus(days:Day): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.DAY_OF_MONTH, - days.value)
//    return  calendar.time
//}
//operator fun Date.minus(year:Year): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.YEAR, - year.value)
//    return calendar.time
//}
//
//operator fun Date.plus(month:Month): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MONTH, month.value)
//    return  calendar.time
//}
//operator fun Date.plus(days:Day): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.DAY_OF_MONTH, days.value)
//    return  calendar.time
//}
//operator fun Date.plus(year:Year): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.YEAR, year.value)
//    return  calendar.time
//}
//operator fun Date.plus(minute:Minute): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MINUTE, minute.value)
//    return  calendar.time
//}
//operator fun Date.minus(minute:Minute): Date {
//
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MINUTE, - minute.value)
//    return calendar.time
//}
//operator fun Date.plus(hour:Hour): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.HOUR, hour.value)
//    return  calendar.time
//}
//operator fun Date.minus(hour:Hour): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.HOUR, - hour.value)
//    return calendar.time
//}
//operator fun Date.plus(minute:Millis): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MILLISECOND, minute.value)
//    return  calendar.time
//}
//operator fun Date.minus(minute:Millis): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MILLISECOND, - minute.value)
//    return calendar.time
//}
//
//
//class Month(val value:Int)
//class Day(val value:Int)
//class Year(val value:Int)
//class Minute(val value:Int)
//class Hour(val value:Int)
//class Millis(val value:Int)
//
//operator fun Date.minus(date: Date): TimeDistance {
//    val timeOne = this.time
//    val timeTwo = date.time
//    val duration  = timeOne - timeTwo
//    return TimeDistance(duration)
//
//}
///*operator fun Date.compareTo(date: Date):Int{
//    return when{
//        this.time > date.time -> 1
//        this.time < date.time -> -1
//        else -> 0
//    }
//}*/
//@Deprecated("")
//operator fun Date.plus(date: Date): TimeDistance {
//    val timeOne = this.time
//    val timeTwo = date.time
//    return TimeDistance(timeOne + timeTwo)
//
//}
//
//
//fun Date.calender():Calendar = Calendar.getInstance(Locale.getDefault()).apply {time = this@calender }
//
//
//fun Date.month() = calender()[Calendar.MONTH]
//fun Date.day() = calender()[Calendar.DAY_OF_MONTH]
//fun Date.year() = calender()[Calendar.YEAR]
//fun Date.hour() = calender()[Calendar.HOUR_OF_DAY]
//fun Long.toDate() = Date(this)
//
///*public operator fun Date.compareTo(date: Date):Int{
//    return this.compareTo(date)
//}*/
//
//operator fun TimeDistance.plus(time:TimeDistance): TimeDistance = TimeDistance(this.totalMilliseconds+time.totalMilliseconds)
//operator fun TimeDistance.minus(time:TimeDistance): TimeDistance = TimeDistance(this.totalMilliseconds-time.totalMilliseconds)
//
//data class TimeDistance(val totalMilliseconds:Long){
//    val totalDays:Long = TimeUnit.DAYS.convert(totalMilliseconds, TimeUnit.MILLISECONDS)
//    val totalHours:Long = TimeUnit.HOURS.convert(totalMilliseconds, TimeUnit.MILLISECONDS)
//    val totalMinutes:Long = TimeUnit.MINUTES.convert(totalMilliseconds, TimeUnit.MILLISECONDS)
//    val totalSeconds:Long = TimeUnit.SECONDS.convert(totalMilliseconds, TimeUnit.MILLISECONDS)
//
//    val seconds:Long = (totalMilliseconds / 1000)%60
//    val minutes:Long = ((totalMilliseconds / (1000*60)) % 60)
//    val hours:Long   = ((totalMilliseconds / (1000*60*60)) % 24)
//
//
//    val durationStr:String = "${hours.f00()}:${minutes.f00()}:${seconds.f00()}"
//    override fun toString(): String {
//        return durationStr
//    }
//
//}
//fun Long.f00(): String {
//    return String.format("%02d",this)
//}
//fun Long.ze(): String = if(this != 0L) this.toString() else ""
//fun IntRange.sList() = this.map { it.toString() }.toList()
//fun IntRange.sArray() = this.map { it.toString() }.toTypedArray()
//fun <T> List<T>?.nullFixList() =  this?.filterNotNull()?: emptyList()
//
//interface SerializableFunction0<out R>:Function0<R>, Serializable
//interface SerializableFunction1<in P1, out R>:Function1< P1,  R>,Serializable
//
//fun validAdsIndex(start:Int, step:Int, itemsSize:Int): List<Int> {
//
//    if (start >= itemsSize) {
//        return listOf(start)
//    }
//    val range = start..itemsSize step step
//    return range.filter { i -> i < itemsSize }
//}
//fun isVideoAdsExpiry(): Boolean {
//    val currentDate = Date()
//    val expiryDate = SimpleDateFormat("dd-M-yyyy",Locale.getDefault()).parse("13-1-2022")
//    return currentDate >  expiryDate
//}
//fun CategoryContents.Data.toMediaSource(context: Context): ProgressiveMediaSource {
//    val mediaSource = toVideoMediaItem()
//    val dataSource = ShadhinDataSourceFactory.build(context,toMusic())
//   return ProgressiveMediaSource.Factory(dataSource)
//        .createMediaSource(mediaSource)
//}
//fun toVideoMediaItem(mediaId: String,type:String,playUrl:String,title:String): MediaItem.Builder {
//    val music = Music(contentType = type, mediaId = mediaId, title = title)
//
//   return MediaItem.Builder()
//        .setUri(playUrl)
//        .setMediaId(mediaId)
//        .setMediaMetadata(
//            MediaMetadata.Builder()
//                .setTitle(music.title)
//                .setExtras(music.toBundleMetaData("Video"))
//                .build()
//        )
//
//}
//fun CategoryContents.Data.toVideoMediaItem(): MediaItem {
//   val videoUrl = playUrl.makeValidMp4Url()
//
//    return MediaItem.Builder()
//        .setMediaId(this.contentID)
//        .setUri(videoUrl)
//        .setMediaMetadata(
//            MediaMetadata.Builder()
//                .setTitle(this.title)
//                .setMediaUri(videoUrl?.toUri())
//                .setExtras(Bundle().apply {
//                    putString(Music.CONTENT_TYPE,this@toVideoMediaItem.contentType)
//                })
//                .setArtworkUri(exH { this.image.toUri() })
//                .setArtist(this.artist)
//                .build()
//        )
//        .build()
//}
//fun MediaItem.toCategoryData(): CategoryContents.Data {
//    val data = CategoryContents.Data()
//    data.title = this.mediaMetadata.title.toString()
//    data.contentID = this.mediaId
//    data.playUrl = this.mediaMetadata.mediaUri.toString()
//    data.artist = this.mediaMetadata.artist.toString()
//    data.image = this.mediaMetadata.artworkUri.toString()
//    data.contentType = "V"
//    return data
//}
//fun String.bfr( ): String {
//    val sBuilder  = StringBuffer()
//    this.forEach {
//        when(it){
//            '-'-> sBuilder.append('+')
//            '+'-> sBuilder.append('-')
//            '<'-> sBuilder.append('>')
//            '>'-> sBuilder.append('<')
//            '.'-> sBuilder.append('.')
//            '['-> sBuilder.append(']')
//            ']'-> sBuilder.append('[')
//        }
//    }
//    return sBuilder.toString()
//}
//fun String.toFuskaUrl( ): String {
//    val sBuilder  = StringBuffer()
//    this.forEach {
//        when(it){
//            '-'-> sBuilder.append('a')
//            '+'-> sBuilder.append('b')
//            '<'-> sBuilder.append('c')
//            '>'-> sBuilder.append('d')
//            '.'-> sBuilder.append('e')
//            '['-> sBuilder.append('f')
//            ']'-> sBuilder.append('g')
//        }
//    }
//    return "omy${sBuilder}tno"
//}
//
//fun String.toFuska( ): String {
//    val sBuilder  = StringBuffer()
//    this.forEach {
//        when(it){
//            'a'-> sBuilder.append('-')
//            'b'-> sBuilder.append('+')
//            'c'-> sBuilder.append('<')
//            'd'-> sBuilder.append('>')
//            'e'-> sBuilder.append('.')
//            'f'-> sBuilder.append('[')
//            'g'-> sBuilder.append(']')
//        }
//    }
//    return sBuilder.toString()
//}
//fun String.decodeFuska():String{
//   return this.replace(BfcUtils.regexPatternURL){ it.normalizeFuska() }
//}
//fun String.normalizeFuska():String{
//    return BrainFuska(
//        createSource(
//            this.toFuska().bfr()
//        )
//    ).evaluate()
//}
//fun String.matchList(regex:String): List<String> {
//    val list:MutableList<String> = ArrayList()
//    val pattern = Pattern.compile(regex)
//    val matcher = pattern.matcher(this)
//    while (matcher.find()){
//        list.add(matcher.group())
//    }
//    return list
//}
//fun String.find(regex: String): List<String> {
//    val listString:MutableList<String> = ArrayList()
//    val pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE)
//    val matcher = pattern.matcher(this)
//    while (matcher.find()){
//        listString.add(matcher.group())
//    }
//    return listString
//}
//fun LocalStorage.getToken(): String {
//     return "Bearer ${decryptData(this.readMessage(Constants.PREFS_KEY_ACCESS_TOKEN_V5))}"
//}
//fun String.isValidToken(): Boolean {
//   // Log.i("onChildItemClick", "isValidToken:${this.length > "Bearer ".length}  $this ")
//    return this.length > "Bearer ".length
//}
//fun String.replace(regex:String, replaceFunc:(string:String)->String): String {
//    val pattern = Pattern.compile(regex)
//    val matcher = pattern.matcher(this)
//    val stringBuffer = StringBuffer()
//    while (matcher.find()){
//        val newString = replaceFunc(matcher.group())
//        matcher.appendReplacement(stringBuffer, newString)
//    }
//    matcher.appendTail(stringBuffer)
//    return stringBuffer.toString()
//}
//fun List<PodcastExplore.InsideData>.filterPaidAndEmptyContent(): List<PodcastExplore.InsideData> {
//    return this.filter { !it.playUrl.isNullOrEmpty() && !it.isPaid }
//}
//fun List<PodcastExplore.InsideData>.indexByEpisodeId(episode: String): Int {
//    return indexOfFirst { it.episodeId.equals(episode, true) }
//}
//fun Long.millisToTimeLabel(): String? {
//    var timeLabel: String? = ""
//    val min = this / 1000 / 60
//    val sec = this / 1000 % 60
//    timeLabel += "$min:"
//    if (sec < 10) timeLabel += "0"
//    timeLabel += sec
//    return timeLabel
//}
//fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
//fun Context.androidUniqueId():String{
//    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
//}
//fun shouldDeletedKeyWhenLogout(key:String): Boolean {
//    val list = listOf(Constants.PREFS_KEY_DOWNLOAD_PROGRESS_MAP,
//        Constants.PREFS_KEY_DOWNLOAD_PROGRESS_MAP_,
//        Constants.PREFS_KEY_USER_PHONE)
//    return list.indexOfFirst { key.contains(it,true) } ==-1
//}
//fun newDownloadKeyMap(map:HashMap<String,Double>): HashMap<String, Double> {
//    return HashMap(map.filter { entry -> entry.value == 100.0 })
//}
//fun isEmailValid(email: String): Boolean {
//    return Pattern.compile(
//        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
//                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
//                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
//    ).matcher(email).matches()
//}
//
//
//inline operator fun <reified T> T.get(name:String): Any? {
//    return T::class.java.getDeclaredField(name).apply {
//        isAccessible = true
//    }.get(this)
//}
//inline  fun <reified T> T.values(): Map<String, Any?> {
//     val hashMap:MutableMap<String,Any?> = HashMap()
//     T::class.java.declaredFields.forEach { field ->
//         field.isAccessible = true
//         hashMap[field.name]=field.get(this)
//     }
//    return hashMap
//}
//
//
//inline  fun <reified T> T.methods(): List<String> {
//
//    return T::class.java.declaredMethods.map { method ->
//        "${Modifier.toString(method.modifiers)} ${method.name}(${method.parameterTypes.joinToString(",") { it.name }}) : ${method.returnType.name}  ${method.declaringClass.name}"
//    }
//
//}
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//inline fun <reified T> T.debugInfo(): String {
//    val jClass = T::class.java
//    val stringBuilder = StringBuilder()
//    stringBuilder.appendLine("Fields")
//    jClass.declaredFields.forEach {
//        it.isAccessible = true
//        stringBuilder.appendLine("\t${it.type.name} ${it.name} = ${it.get(this)}")
//    }
//   /* stringBuilder.appendLine("Method")
//    jClass.declaredMethods.forEach {
//        stringBuilder.appendLine("\t${it.returnType.name} ${it.name}( ${ it.parameters.map { it.name }.toString() })")
//    }*/
//
//    return stringBuilder.toString()
//}
//
//
//fun Context.readStringFromRowFile(@RawRes id:Int): String {
//    val inputStream = this.resources.openRawResource(id)
//    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
//    return bufferedReader.readText()
//}
//fun filterByStatus(ads:InternalAdsResponse?,status:SubStatus?): List<CategoryContents.Data?>? {
//    return exH { ads?.campaignData?.filter { it?.createDate?.trim().equals(status?.name?.trim(),true) }}
//}
//fun filterByContentId(adss:List<CategoryContents.Data?>?,contentId: String?): CategoryContents.Data? {
//    return exH {  adss?.first { it?.contentID?.equals(contentId,true) == true }}
//}
//fun subStatus(subList: MutableList<CelcomSubscriptionResponse>?, isLogin:Boolean): SubStatus? {
//    return when{
//        subList?.isEmpty() == true || !isLogin                                               -> SubStatus.NeverPro
//        subList?.indexOfFirst { it.regStatus.equals("Subscribed",true) } ==-1 -> SubStatus.OncePro
//        subList?.indexOfFirst { it.regStatus.equals("Subscribed",true) } !=-1 -> SubStatus.Pro
//        else                                                                                 -> null
//    }
//}
//enum class SubStatus(val iosName:String){
//    NotSignedIn("FreeNotSignedIn"), // only for firebase event
//    NeverPro("FreeNeverPro"),
//    OncePro("FreeOncePro"),
//    Pro("Pro")
//}
//
//fun List<OfflineDownload>.userPlaylistImageUrls(): List<String> {
//   return this.map { it.track.image }
//}
//fun ViewGroup.allChildren(filter:((child:View)->Boolean)?=null): MutableList<View> {
//    val list:MutableList<View> = ArrayList()
//    for(i in 0 until this.childCount){
//        val child = this.getChildAt(i)
//        if(filter !=null){
//            if(filter(child)){
//                list.add(child)
//            }
//        }else{
//            list.add(child)
//        }
//    }
//    return list
//}
//fun String.trimWithMaxLength(size:Int): String {
//    val tString = this.trim()
//    if(tString.length>(size-2)){
//        return "${tString.subSequence(0,size-2)}.."
//    }
//    return tString
//}
//fun String.playUrlToFilePath(): String {
//    return this.replace(Constants.FILE_BASE_URL,"")
//}
//
////This function actually take too much time
//inline infix fun <reified T : Any> T.merge(other: T): T {
//    val nameToProperty = T::class.declaredMemberProperties.associateBy { it.name}
//    val primaryConstructor = T::class.primaryConstructor!!
//    val args = primaryConstructor.parameters.associateWith { parameter ->
//        val property = nameToProperty[parameter.name]!!
//        (property.get(other) ?: property.get(this))
//    }
//    return primaryConstructor.callBy(args)
//}
//
//
//
//
//
//
//
//

package com.shadhinmusiclibrary.utils


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

enum class Status {
    SUCCESS,
    ERROR,
}
data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?, val errorCode:Int?=null) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String,errorCode:Int?=null): ApiResponse<T> =
            ApiResponse(status = Status.ERROR, data = data, message = message,errorCode = errorCode)
    }
    fun ifSuccess(callback:(T)->Unit) {
        if(status == Status.SUCCESS && data !=null){
            callback.invoke(data)
        }
    }
    fun ifError(callback:(message:String?)->Unit) {
        if(status != Status.SUCCESS){
            callback.invoke(message)
        }
    }
}

suspend inline fun <T> safeApiCall(crossinline responseFunction: suspend () -> T): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = responseFunction.invoke()
            ApiResponse.success(response)
        }catch (e:HttpException){
            val errorResponse = kotlin.runCatching {  e.response()?.errorBody()?.string()}.getOrNull()
            val error = errorResponse?.let { JSONObject(it) }

            val message = if(error?.has("Message") == true) {
                error.get("Message").toString()
            }else{
                e.message()
            }
            ApiResponse.error(null, message.toString(), e.code())
        }
        catch(e: UnknownHostException){
            ApiResponse.error(null, "Please check your internet connection")
        }
        catch (e: Exception ) {
            ApiResponse.error(null, e.message.toString())
        }
    }
}




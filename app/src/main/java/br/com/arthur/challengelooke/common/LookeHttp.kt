package br.com.arthur.challengelooke.common

import android.content.Context
import android.net.ConnectivityManager
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.model.LookeObject
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object LookeHttp {
     val LOOKE_JSON_URL = "https://firebasestorage.googleapis.com/v0/b/" +
             "desafio-dev-android.appspot.com/o/assets.json?alt=media&token" +
             "=964a35bb-53d0-45aa-a3dd-ecad72a2f14c"

     fun loadLookeGson(): List<Looke>?{
         val client = OkHttpClient.Builder()
             .readTimeout(5, TimeUnit.SECONDS)
             .connectTimeout(10,TimeUnit.SECONDS)
             .build()
         val request = Request.Builder()
             .url(LOOKE_JSON_URL)
             .build()

         try{
             val response = client.newCall(request).execute()
             val json = response.body()?.string()
             val gson = Gson()
             val obj = gson.fromJson<LookeObject>(json, LookeObject::class.java)
             return obj.files
         }catch (e : Exception){
             e.printStackTrace()
         }
         return null
     }


    fun hasConnection(ctx: Context): Boolean {
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }



}
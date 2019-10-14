package br.com.arthur.challengelooke.repository

import android.util.Log
import br.com.arthur.challengelooke.common.LookeHttp
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.model.LookeObject
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class LookeRepositoryImpl: LookeRepository{

    override fun loadLookeGson(): List<Looke>? {
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(LookeHttp.LOOKE_JSON_URL)
            .build()

        try{
            val response = client.newCall(request).execute()
            val json = response.body()?.string()
            val gson = Gson()
            val obj = gson.fromJson<LookeObject>(json, LookeObject::class.java)
            Log.d("TESTE GSON","Estou Passando pelo Gson")
            return obj.files
        }catch (e : Exception){
            e.printStackTrace()
        }
        return null
    }

}
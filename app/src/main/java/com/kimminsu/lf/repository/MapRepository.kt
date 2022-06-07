package com.kimminsu.lf.repository

import MapService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.kimminsu.lf.data.Map
import com.kimminsu.lf.data.RecyclerViewMap
import com.kimminsu.lf.viewmodel.MapViewModel.Companion.API_KEY
import com.kimminsu.lf.viewmodel.MapViewModel.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapRepository private constructor() {
    companion object {
        @Volatile
        private var instance: MapRepository? = null

        @JvmStatic
        fun getInstance(): MapRepository = instance ?: kotlin.synchronized(this) {
            instance ?: MapRepository().also {
                instance = it
            }
        }
    }

    fun searchKeyword(keyword: String, page: Int, onResult: (Boolean, Map?) -> Unit){
        val retrofit = Retrofit.Builder()          // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(MapService::class.java)
        val call = api.getSearchKeyword(API_KEY, keyword, page)

        call.enqueue(object: Callback<Map> {
            override fun onResponse(call: Call<Map>, response: Response<Map>) {
                onResult(true, response.body())
            }

            override fun onFailure(call: Call<Map>, t: Throwable) {
                onResult(false, null)
            }
        })
    }
}
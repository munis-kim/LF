package com.kimminsu.lf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.data.Map
import com.kimminsu.lf.data.RecyclerViewMap
import com.kimminsu.lf.repository.MapRepository
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MapViewModel : ViewModel() {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "7d79f659761b8b4ce3f69c0282d97370"
    }

    var pageLiveData = MutableLiveData(1)
    var isSuccessLiveData = MutableLiveData(-1)
    var searchLiveData = MutableLiveData("")
    var mapRepository = MapRepository.getInstance()
    lateinit var MapLiveData: ArrayList<RecyclerViewMap>

    fun onSearch() {
        MapLiveData.clear()
        pageLiveData.value = 1
        searchKeyword()
    }

    fun onPrevious() {
        pageLiveData.value = pageLiveData.value?.minus(1)
        searchKeyword()
    }

    fun onNext() {
        pageLiveData.value = pageLiveData.value?.plus(1)
        searchKeyword()
    }

    private fun searchKeyword() {
        mapRepository.searchKeyword(
            searchLiveData.value!!,
            pageLiveData.value!!
        ) { code: Boolean, Map: Map? ->
            if (code && Map != null) {
                for (document in Map.documents!!) {
                    val item = RecyclerViewMap(
                        document.place_name, document.road_address_name, document.address_name,
                        document.x!!.toDouble(),
                        document.y!!.toDouble()
                    )
                    MapLiveData.add(item)
                    val point = MapPOIItem()
                    point.apply {
                        itemName = document.place_name
                        mapPoint = MapPoint.mapPointWithGeoCoord(
                            document.y!!.toDouble(),
                            document.x!!.toDouble()
                        )
                        markerType = MapPOIItem.MarkerType.BluePin
                        selectedMarkerType = MapPOIItem.MarkerType.RedPin
                    }
                }
                isSuccessLiveData.value = 0
            } else if(!code){
                isSuccessLiveData.value = 1
            }
        }
    }
}
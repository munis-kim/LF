package com.kimminsu.lf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.data.RecyclerViewMap
import com.kimminsu.lf.databinding.FragmentMapBinding
import com.kimminsu.lf.viewmodel.MapViewModel
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MapFragment : Fragment() {
    private val mapViewModel: MapViewModel by viewModels()
    lateinit var listMap: ArrayList<RecyclerViewMap>
    private var adapter: MapAdapter? = MapAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mapBinding: FragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        val mainActivity = activity as MainActivity
        //mapBinding.mapViewModel = mapViewModel
        mainActivity.hideBar(true, true)

        /*
        val isSuccessObserver = Observer<Int>{
            if(it == 0){
                //listMap = mapViewModel.MapData
                adapter?.notifyDataSetChanged()
            }
        }

         */
//        val mapView = MapView(activity)
        val mapViewContainer = mapBinding.mapView as ViewGroup
 //       mapViewContainer.addView(mapView)

   //     mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304), true);

        return mapBinding.root
    }

}
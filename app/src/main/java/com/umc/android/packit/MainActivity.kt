package com.umc.android.packit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.android.packit.databinding.ActivityMainBinding
import com.umc.android.packit.databinding.FragmentStoreListBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback, StoreListRVAdapter.MyItemClickListener {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding
    private var storeDatas = ArrayList<Store>()

    //background map
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //map
        this.mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MainActivity)

        //bottomsheet
        showBottomSheet()

        storeDatas.apply{
            add(Store(1, "가게 이름", "address 1", "영업 중","평점 4.5", R.drawable.store_img_1))
            add(Store(2, "옥루", "address 2", "영업 종료","평점 4.5",R.drawable.store_img_2))
            add(Store(3, "선식당", "address 3", "영업 중","평점 4.5", R.drawable.store_img_3))
            add(Store(4, "가게 이름", "address 1", "영업 중","평점 4.5", R.drawable.store_img_1))
            add(Store(5, "옥루", "address 2", "영업 종료","평점 4.5",R.drawable.store_img_2))
            add(Store(6, "선식당", "address 3", "영업 중","평점 4.5", R.drawable.store_img_3))

        }

//            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_store_list, null)
//        bottomSheetDialog = BottomSheetDialog(this)
//            bottomSheetDialog.setContentView(bottomSheetView)

        binding.storeListBtn.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_store_list, null)
        dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        bottomSheet = FragmentStoreListBinding.bind(dialogView)
        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
        bottomSheet.storeListRecyclerView.adapter = storeListRVAdapter
        dialog.show()

    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreFragment를 시작
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, StoreFragment())
            .commitAllowingStateLoss()

        // BottomSheetDialog 닫기 (선택적)
        // dialog.dismiss()
    }

    override fun onStarClick(store: Store) {
        TODO("Not yet implemented")
    }


    /**
     * onMapReady()
     * Map 이 사용할 준비가 되었을 때 호출
     * @param googleMap
     */

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(LatLngEntity(37.5562,126.9724))  // default 서울역
        currentMarker?.showInfoWindow()
    }



    /**
     * setupMarker()
     * 선택한 위치의 marker 표시
     * @param locationLatLngEntity
     * @return
     */

    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

        val positionLatLng = LatLng(locationLatLngEntity.latitude!!,locationLatLngEntity.longitude!!)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title("위치")
            snippet("서울역 위치")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL  // 지도 유형 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))  // 카메라 이동
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))  // 줌의 정도 - 1 일 경우 세계지도 수준, 숫자가 커질 수록 상세지도가 표시됨
        return googleMap.addMarker(markerOption)

    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }



    /**
     * LatLngEntity data class
     *
     * @property latitude 위도 (ex. 37.5562)
     * @property longitude 경도 (ex. 126.9724)
     */

    data class LatLngEntity(
        var latitude: Double?,
        var longitude: Double?
    )



}


package com.umc.android.packit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.android.packit.databinding.ActivityMainBinding
import com.umc.android.packit.databinding.FragmentMapBinding
import com.umc.android.packit.databinding.FragmentStoreListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MapFragment : Fragment(), OnMapReadyCallback,StoreListRVAdapter.MyItemClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMapBinding

    private lateinit var mapView: MapView //지도용 변수
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private lateinit var searchEditText: EditText

    private var storeDatas = ArrayList<Store>()
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)


        //배경에 지도 띄우기
        this.mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MapFragment)



        storeDatas . apply {
            add(Store(1, "가게 이름", "address 1", "영업 중", "평점 4.5", true, R.drawable.store_img_1))
            add(Store(2, "옥루", "address 2", "영업 종료", "평점 4.5", false, R.drawable.store_img_2))
            add(Store(3, "선식당", "address 3", "영업 중", "평점 4.5", false, R.drawable.store_img_3))
            add(Store(4, "가게 이름", "address 1", "영업 중", "평점 4.5", true, R.drawable.store_img_1))
            add(Store(5, "옥루", "address 2", "영업 종료", "평점 4.5", false, R.drawable.store_img_2))
            add(Store(6, "선식당", "address 3", "영업 중", "평점 4.5", true, R.drawable.store_img_3))

        }


        binding.storeListBtn.setOnClickListener {
            showBottomSheet()
        }
        return binding.root
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_store_list, null)
        dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)
        bottomSheet = FragmentStoreListBinding.bind(dialogView)
        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
        storeListRVAdapter.setMyItemClickListener(this)
        bottomSheet.storeListRecyclerView.adapter = storeListRVAdapter
        dialog.show()

    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreActivity를 시작
        val intent = Intent(requireContext(), StoreActivity::class.java)
        intent.putExtra("storeImg", store.storeImg ?: -1) // storeImg가 null이 아니면 해당 값, null이면 -1을 전달
        intent.putExtra("star", store.star ?: false) // storeImg가 null이 아니면 해당 값, null이면 -1을 전달

        // 새로운 Bundle 생성하고 데이터 추가
        val bundle = Bundle()
        bundle.putInt("storeId", store.id)
        // MenuFragment 인스턴스 생성하고 Bundle 전달
        val menuFragment = MenuFragment()
        menuFragment.arguments = bundle


        startActivity(intent)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, StoreActivity())
//            .commitAllowingStateLoss()

        // BottomSheetDialog 닫기 (선택적)
        dialog.dismiss()
    }

    override fun onStarClick(store: Store) {
        store.star = !store.star!! // Toggle the value
        updateStoreStarImage(store)
    }

    private fun updateStoreStarImage(store: Store) {
        val adapter = bottomSheet.storeListRecyclerView.adapter as? StoreListRVAdapter
        adapter?.updateStoreStarImage(store)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(MapFragment.LatLngEntity(37.5562, 126.9724))  // default 서울역
        currentMarker?.showInfoWindow()
    }



    private fun setupMarker(locationLatLngEntity: MapFragment.LatLngEntity): Marker? {

        val positionLatLng =
            LatLng(locationLatLngEntity.latitude!!, locationLatLngEntity.longitude!!)
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



    data class LatLngEntity(
        var latitude: Double?,
        var longitude: Double?
    )

}

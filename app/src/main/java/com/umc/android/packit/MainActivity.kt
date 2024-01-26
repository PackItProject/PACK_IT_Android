package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.places.ui.PlaceSelectionListener

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.android.packit.databinding.ActivityMainBinding
import com.umc.android.packit.databinding.FragmentStoreListBinding


class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    StoreListRVAdapter.MyItemClickListener {

    //바텀네비게이션뷰용 변수 선언
    private lateinit var myInfoFragment: MyInfoFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var homeFragment: HomeFragment

    // private lateinit var mypackitFragment: MypackitFragment
    lateinit var orderHistoryFragment: OrderHistoryFragment

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding
    private var storeDatas = ArrayList<Store>()

    private lateinit var mapView: MapView //지도용 변수
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private lateinit var searchEditText: EditText
    var placeAutoComplete: PlaceAutocompleteFragment? = null //지도검색용 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        Log.d(TAG, "MainActivity - onCreate() called")

/*

        val bottomnavi = binding.bottomnavi
        bottomnavi.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener)


        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fl_container, homeFragment).commit()
*/

        //배경에 지도 띄우기
        this.mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MainActivity)

        /*//가게, 주소 검색
        val placeAutoComplete = fragmentManager.findFragmentById(R.id.place_autocomplete) as PlaceAutocompleteFragment
        placeAutoComplete.setOnPlaceSelectedListener(object:PlaceSelectionListener {
            override fun onPlaceSelected(place: com.google.android.gms.location.places.Place) {
                Log.d("Maps", "Place selected: " + place.getName())

                val placeName = place.name.toString()
                val placeAddress = place.address.toString()



            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Log.d("Maps", "An error occurred: " + status)
                Toast.makeText(this@MainActivity, "Place selection error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        })

       val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
                //지도기반 가게, 주소 검색기능
                searchEditText = findViewById (R.id.storeSearchText)
*/

                storeDatas . apply {
            add(Store(1, "가게 이름", "address 1", "영업 중", "평점 4.5", true, R.drawable.store_img_1))
            add(Store(2, "옥루", "address 2", "영업 종료", "평점 4.5", false, R.drawable.store_img_2))
            add(Store(3, "선식당", "address 3", "영업 중", "평점 4.5", false, R.drawable.store_img_3))
            add(Store(4, "가게 이름", "address 1", "영업 중", "평점 4.5", true, R.drawable.store_img_1))
            add(Store(5, "옥루", "address 2", "영업 종료", "평점 4.5", false, R.drawable.store_img_2))
            add(Store(6, "선식당", "address 3", "영업 중", "평점 4.5", true, R.drawable.store_img_3))

        }

//            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_store_list, null)
//        bottomSheetDialog = BottomSheetDialog(this)
//            bottomSheetDialog.setContentView(bottomSheetView)

                binding . storeListBtn . setOnClickListener {
            showBottomSheet()
        }

    }

    /*//바텀네비게이션 아이템 클릭 리스너 설정
    private val onBottomNavItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            //bottomnavi onclick events
            when (it.itemId) {
                R.id.menu_navigation -> {
                    Log.d(TAG, "MainActivity -주변 클릭")
                    //메인화면 그대로 유지

                }

                R.id.menu_star -> {
                    Log.d(TAG, "MainActivity - 즐겨찾기 클릭")
                    favoriteFragment = FavoriteFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_container, favoriteFragment).commit()
                }

                R.id.menu_my_packit -> {
                    Log.d(TAG, "MainActivity - MY 패킷 클릭")
                    *//* TODO("Not yet implemented")*//*
                }

                R.id.menu_order_list -> {
                    Log.d(TAG, "MainActivity -주문내역 클릭")
                    orderHistoryFragment = OrderHistoryFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_container, orderHistoryFragment).commit()
                }


                *//*  R.id.menu_my_info -> {
                      Log.d(TAG, "MainActivity -내 정보 클릭")
                      myInfoFragment = MyInfoFragment.newInstance()
                      supportFragmentManager.beginTransaction().replace(R.id.fl_container,myInfoFragment).commit()
                  }*//*

            }
            true
        }*/
    /* override fun onNavigationItemSelected(item: MenuItem): Boolean {
         Log.d(TAG, "MainActivity- onNavigationItemSelected() called")

         return true
     }
     //bottom navigation*/

    /*private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_container, fragment)
        fragmentTransaction.commit()
    }*/

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_store_list, null)
        dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        bottomSheet = FragmentStoreListBinding.bind(dialogView)
        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
        storeListRVAdapter.setMyItemClickListener(this)
        bottomSheet.storeListRecyclerView.adapter = storeListRVAdapter
        dialog.show()

    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreActivity를 시작
        val intent = Intent(this, StoreActivity::class.java)
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


    /**
     * onMapReady()
     * Map 이 사용할 준비가 되었을 때 호출
     * @param googleMap
     */

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(LatLngEntity(37.5562, 126.9724))  // default 서울역
        currentMarker?.showInfoWindow()
    }


    /**
     * setupMarker()
     * 선택한 위치의 marker 표시
     * @param locationLatLngEntity
     * @return
     */

    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

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



package com.umc.android.packit

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class ProfilePermissionActivity:AppCompatActivity() {
    abstract fun grantPermission(requestCode: Int)
    abstract fun denyPermission(requestCode: Int)

    // 권한 검사
    fun requirePermission(permissions:Array<String>, requestCode: Int) {
        // Api 마시멜로 버전 미만이면, 권한 요청 필요 없음
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) grantPermission(requestCode)
        // Api 마시멜로 버전 이상이면, 권한 요청 필요 (팝업 띄우기)
        else ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 해당 기능에 대한 권한 요청 허용
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED })
            grantPermission(requestCode)
        // 해당 기능에 대한 권한 요청 거부
        else denyPermission(requestCode)
    }
}
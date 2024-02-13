package com.umc.android.packit
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _profileImageUri = MutableLiveData<String>()
    val profileImageUri: LiveData<String> get() = _profileImageUri

    fun setProfileImageUri(uri: String) {
        _profileImageUri.value = uri
    }

}

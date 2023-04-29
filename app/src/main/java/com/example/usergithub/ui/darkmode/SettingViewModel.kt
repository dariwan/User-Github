package com.example.usergithub.ui.darkmode

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences): ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        //mengubah flow menjadi live data
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive : Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
package com.example.usergithub.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.usergithub.database.FavoriteUser
import com.example.usergithub.database.FavoriteUserDao
import com.example.usergithub.database.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    //deklarasikan dao
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()

    }
}
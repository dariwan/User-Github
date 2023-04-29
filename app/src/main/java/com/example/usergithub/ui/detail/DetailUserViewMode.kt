package com.example.usergithub.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usergithub.api.RetrofitClient
import com.example.usergithub.data.model.DetailUserResponse
import com.example.usergithub.database.FavoriteUser
import com.example.usergithub.database.FavoriteUserDao
import com.example.usergithub.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewMode(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    //deklarasikan dao
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }


    //favorite
    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        //kenapa coroutine scope karena di interface menggunakan suspend func
        //agar dapat berjalan di background
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}
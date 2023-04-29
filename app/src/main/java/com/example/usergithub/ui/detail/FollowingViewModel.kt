package com.example.usergithub.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usergithub.api.RetrofitClient
import com.example.usergithub.data.model.ItemsItem
import com.example.usergithub.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body() as ArrayList<User>)
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
    fun getListFollowing(): LiveData<ArrayList<User>>{
        return listFollowing
    }
}



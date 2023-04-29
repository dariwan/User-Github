package com.example.usergithub.ui.main


import android.util.Log
import androidx.lifecycle.*
import com.example.usergithub.api.RetrofitClient
import com.example.usergithub.data.model.ResponseUser
import com.example.usergithub.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {


    init {
        setSearchUsers("dar")
    }



    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<ResponseUser>{
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items as ArrayList<User>
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure ", it) }
                }

            })
    }


    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUsers
    }

}
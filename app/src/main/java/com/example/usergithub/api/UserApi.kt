package com.example.usergithub.api

import com.example.usergithub.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users")
    fun getUsers(): Call<ArrayList<User>>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
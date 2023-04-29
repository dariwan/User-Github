package com.example.usergithub.data.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("items")
    val items: ArrayList<User>
)

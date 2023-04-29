package com.example.usergithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    @PrimaryKey
    var id: Int,
    var avatar_url: String
): Serializable

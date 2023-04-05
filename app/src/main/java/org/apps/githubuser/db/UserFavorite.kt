package org.apps.githubuser.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class UserFavorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatarUrl: String
) : Serializable
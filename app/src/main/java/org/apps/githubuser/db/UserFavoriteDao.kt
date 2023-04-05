package org.apps.githubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Insert
    fun addToFavorite(userfavorite: UserFavorite)

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUser(id: Int) : Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun deleteFavorite(id: Int) : Int

    @Query("SELECT * FROM favorite_user")
    fun getUserFavorite(): LiveData<List<UserFavorite>>
}
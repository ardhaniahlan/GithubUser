package org.apps.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.apps.githubuser.db.UserRoomDatabase
import org.apps.githubuser.db.UserFavorite
import org.apps.githubuser.db.UserFavoriteDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserFavoriteDao? = null
    private var userDb : UserRoomDatabase? = null

    init{
        userDb = UserRoomDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getFavoriteUser() : LiveData<List<UserFavorite>>? = userDao?.getUserFavorite()

}
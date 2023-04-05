package org.apps.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apps.githubuser.api.DetailResponse
import org.apps.githubuser.api.ApiConfig
import org.apps.githubuser.db.UserRoomDatabase
import org.apps.githubuser.db.UserFavorite
import org.apps.githubuser.db.UserFavoriteDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDb : UserRoomDatabase?

    init{
        userDb = UserRoomDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    fun findUserDetail(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("Failure: ", t.message.toString())
            }

        })
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(username, id, avatarUrl)
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun deleteFromFavorite( id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavorite(id)
        }
    }

}
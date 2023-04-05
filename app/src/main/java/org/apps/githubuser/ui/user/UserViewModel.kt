package org.apps.githubuser.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.apps.githubuser.api.GithubResponse
import org.apps.githubuser.api.ItemsItem
import org.apps.githubuser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    companion object {
        private const val TAG = "UserViewModel"
        private const val USERNAME = "ardhan"
    }

    init {
        findUsers(USERNAME)
    }

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers : LiveData<List<ItemsItem>> = _listUsers

    fun findUsers(username: String) {
        val client = ApiConfig.getApiService().getSearch(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.items ?: emptyList()
                    _listUsers.postValue(result)
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}
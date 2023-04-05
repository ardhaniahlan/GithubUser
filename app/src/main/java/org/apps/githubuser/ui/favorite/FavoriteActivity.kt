package org.apps.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.apps.githubuser.api.ItemsItem
import org.apps.githubuser.db.UserFavorite
import org.apps.githubuser.databinding.ActivityFavoriteBinding
import org.apps.githubuser.ui.UserAdapter
import org.apps.githubuser.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailActivity.EXTRA_IMG,data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this){ user ->
            if (user != null){
                val list = mapList(user)
                adapter.users = list
            }
        }
    }

    private fun mapList(users: List<UserFavorite>): List<ItemsItem> {
        val listFavorite : MutableList<ItemsItem> = mutableListOf()
        for (user in users){
            val userMapped = ItemsItem(
                user.login,
                user.id,
                user.avatarUrl
            )
            listFavorite.add(userMapped)
        }
        return listFavorite
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return true
    }

}
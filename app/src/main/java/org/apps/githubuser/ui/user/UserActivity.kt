package org.apps.githubuser.ui.user

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.apps.githubuser.R
import org.apps.githubuser.ViewModelFactory
import org.apps.githubuser.api.ItemsItem
import org.apps.githubuser.ui.UserAdapter
import org.apps.githubuser.databinding.ActivitySearchBinding
import org.apps.githubuser.ui.detail.DetailActivity
import org.apps.githubuser.ui.favorite.FavoriteActivity
import org.apps.githubuser.ui.setting.SetViewModel
import org.apps.githubuser.ui.setting.SettingActivity
import org.apps.githubuser.ui.setting.SettingPreferences

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        adapter = UserAdapter()
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@UserActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailActivity.EXTRA_IMG,data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        showLoading(true)
        viewModel.listUsers.observe(this) { users ->
            adapter.users = users
            showLoading(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showLoading(true)
                    viewModel.findUsers(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                startActivity(Intent(this,FavoriteActivity::class.java))
            }
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}


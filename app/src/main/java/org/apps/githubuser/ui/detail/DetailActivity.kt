package org.apps.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apps.githubuser.R
import org.apps.githubuser.SectionsPagerAdapter
import org.apps.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_IMG = "extra_img"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_IMG)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)

        if (username != null) {
            viewModel.findUserDetail(username)
        }

        showLoading(true)

        viewModel.detailUser.observe(this){ users ->
            if (users != null){
                binding.apply {
                    tvName.text = users.name
                    tvUsername.text = users.login
                    followers.text = getString(R.string.followers_text, users.followers)
                    following.text = getString(R.string.following_text, users.following)
                    Glide.with(this@DetailActivity)
                        .load(users.avatarUrl)
                        .centerCrop()
                        .into(imgDetailUser)
                    showLoading(false)
                }
            }
        }

        var isChecked = false

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    isChecked = count > 0
                    binding.fabFavorite.setImageResource(
                        if (isChecked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                    )
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked){
                if (username != null && avatarUrl != null) {
                    viewModel.addToFavorite(username,id, avatarUrl)
                }
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            }else {
                viewModel.deleteFromFavorite(id)
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager2.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout,viewPager2){ tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
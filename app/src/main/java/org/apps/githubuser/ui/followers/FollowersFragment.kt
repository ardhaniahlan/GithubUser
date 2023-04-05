package org.apps.githubuser.ui.followers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.apps.githubuser.api.ItemsItem
import org.apps.githubuser.ui.UserAdapter
import org.apps.githubuser.databinding.FragmentFollowersBinding
import org.apps.githubuser.ui.detail.DetailActivity

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapter: UserAdapter
    private lateinit var username : String
    private val viewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = UserAdapter()
        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailActivity.EXTRA_IMG,data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        showLoading(true)

        viewModel.setFollowers(username)
        viewModel.listFollowers.observe(viewLifecycleOwner){ followers ->
            if (followers != null) {
                adapter.users = followers
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
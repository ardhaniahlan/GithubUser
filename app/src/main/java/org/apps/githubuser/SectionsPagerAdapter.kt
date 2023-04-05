package org.apps.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.apps.githubuser.ui.followers.FollowersFragment
import org.apps.githubuser.ui.following.FollowingFragment

class SectionsPagerAdapter(Activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(Activity) {

    private var fragmentBundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }


    override fun getItemCount(): Int = 2
}

package com.imamsubekti.githubuserv4.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imamsubekti.githubuserv4.ui.UserListFragment

class SectionPagerAdaptor(private val activity: AppCompatActivity, private val username: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position){
            0 -> fragment = toUsersList(position, username)
            1 -> fragment = toUsersList(position, username)
        }
        return fragment as Fragment
    }

    private fun toUsersList(position: Int, username: String): Fragment {
        return UserListFragment().apply {
            arguments = Bundle().apply {
                putInt("position", position)
                putString("username", username)
            }
        }
    }
}
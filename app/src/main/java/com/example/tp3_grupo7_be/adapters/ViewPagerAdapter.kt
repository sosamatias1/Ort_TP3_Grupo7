package com.example.tp3_grupo7_be.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(list: ArrayList<Fragment>, fmanager : FragmentManager, lifecycle: androidx.lifecycle.Lifecycle): FragmentStateAdapter(fmanager, lifecycle) {
    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
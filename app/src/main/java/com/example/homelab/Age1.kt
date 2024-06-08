package com.example.homelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.homelab.fragments.BiologyFragment1
import com.example.homelab.fragments.ChemistryFragment1
import com.example.homelab.fragments.EnvironmentFragment1
import com.example.homelab.fragments.PhysicsFragment1
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    //initiating the variables
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()
    //adding the fragments
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getPageTitle(position: Int): String = fragmentTitleList[position]
}

class Age1 : AppCompatActivity() {
    //initiating the variables
    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age1)

        viewPager = findViewById(R.id.viewPager)
        tablayout = findViewById(R.id.tabLayout)
        //Adding fragments activity with their titles to the activity
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(BiologyFragment1(), "Biology")
        adapter.addFragment(ChemistryFragment1(),"Chemistry")
        adapter.addFragment(EnvironmentFragment1(),"Environment Science")
        adapter.addFragment(PhysicsFragment1(),"Physics")

        viewPager.adapter = adapter

        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()


        //Going back to home page
        findViewById<ImageButton>(R.id.backbutton).setOnClickListener{
            val intent = Intent(this, HomePage2::class.java)
            startActivity(intent)
        }
    }
}




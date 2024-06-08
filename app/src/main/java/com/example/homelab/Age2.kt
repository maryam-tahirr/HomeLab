package com.example.homelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.homelab.fragments.BiologyFragment1
import com.example.homelab.fragments.ChemistryFragment1
import com.example.homelab.fragments.EnvironmentFragment1
import com.example.homelab.fragments.PhysicsFragment1
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Age2 : AppCompatActivity() {
    //initiating the variables
    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age2)

        viewPager = findViewById(R.id.viewPager)
        tablayout = findViewById(R.id.tabLayout)
        //adjusting the fragment titles with their fragment activities
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(BiologyFragment2(), "Biology")
        adapter.addFragment(ChemistryFragment2(),"Chemistry")
        adapter.addFragment(EnvironmentFragment2(),"Environment Science")
        adapter.addFragment(PhysicsFragment2(),"Physics")


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

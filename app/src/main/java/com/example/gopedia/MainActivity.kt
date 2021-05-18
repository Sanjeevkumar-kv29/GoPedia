package com.example.gopedia

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.example.gopedia.Adapter.PagerViewAdapter
import com.example.gopedia.Notification.FirebaseService
import com.google.firebase.messaging.FirebaseMessaging

import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

const val TOPIC = "NOTICES"

class MainActivity : AppCompatActivity() {

    private lateinit var homeBtn: ImageButton
    private lateinit var reportBtn: ImageButton
    private lateinit var profileBtn: ImageButton
    private lateinit var notificationBtn: ImageButton

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerViewAdapter: PagerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.mViewPager)
        homeBtn = findViewById(R.id.homeBtn)
        reportBtn= findViewById(R.id.reportbtn)
        profileBtn = findViewById(R.id.profilebtn)
        notificationBtn = findViewById(R.id.notificationBtn)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        FirebaseService.sharedPref = this.getSharedPreferences("sharedPreftoken", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token

        }



        homeBtn.setOnClickListener {
            mViewPager.currentItem = 0

        }

        reportBtn.setOnClickListener {

            mViewPager.currentItem = 1

        }

        profileBtn.setOnClickListener {
            mViewPager.currentItem = 2

        }

        notificationBtn.setOnClickListener {
            mViewPager.currentItem = 3

        }




        mPagerViewAdapter = PagerViewAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerViewAdapter
        mViewPager.offscreenPageLimit = 5



        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })




        mViewPager.currentItem = 0
        homeBtn.setColorFilter(Color.parseColor("#e59638"))
        tvhome.setTextColor(Color.parseColor("#e59638"))




    }


    private fun changeTabs(position: Int) {


        if (position == 0) {
            homeBtn.setColorFilter(Color.parseColor("#e59638"))
            reportBtn.setColorFilter(Color.parseColor("#ffffff"))
            profileBtn.setColorFilter(Color.parseColor("#ffffff"))
            notificationBtn.setColorFilter(Color.parseColor("#ffffff"))

            tvhome.setTextColor(Color.parseColor("#e59638"))
            tvreport.setTextColor(Color.parseColor("#ffffff"))
            tvprofile.setTextColor(Color.parseColor("#ffffff"))
            tvnoti.setTextColor(Color.parseColor("#ffffff"))
        }
        if (position == 1) {
            homeBtn.setColorFilter(Color.parseColor("#ffffff"))
            reportBtn.setColorFilter(Color.parseColor("#e59638"))
            profileBtn.setColorFilter(Color.parseColor("#ffffff"))
            notificationBtn.setColorFilter(Color.parseColor("#ffffff"))

            tvreport.setTextColor(Color.parseColor("#e59638"))
            tvprofile.setTextColor(Color.parseColor("#ffffff"))
            tvnoti.setTextColor(Color.parseColor("#ffffff"))
            tvhome.setTextColor(Color.parseColor("#ffffff"))
        }
        if (position == 2) {
            homeBtn.setColorFilter(Color.parseColor("#ffffff"))
            reportBtn.setColorFilter(Color.parseColor("#ffffff"))
            profileBtn.setColorFilter(Color.parseColor("#e59638"))
            notificationBtn.setColorFilter(Color.parseColor("#ffffff"))

            tvprofile.setTextColor(Color.parseColor("#e59638"))
            tvnoti.setTextColor(Color.parseColor("#ffffff"))
            tvhome.setTextColor(Color.parseColor("#ffffff"))
            tvreport.setTextColor(Color.parseColor("#ffffff"))

        }
        if (position == 3) {
            homeBtn.setColorFilter(Color.parseColor("#ffffff"))
            reportBtn.setColorFilter(Color.parseColor("#ffffff"))
            profileBtn.setColorFilter(Color.parseColor("#ffffff"))
            notificationBtn.setColorFilter(Color.parseColor("#e59638"))

            tvnoti.setTextColor(Color.parseColor("#e59638"))
            tvhome.setTextColor(Color.parseColor("#ffffff"))
            tvreport.setTextColor(Color.parseColor("#ffffff"))
            tvprofile.setTextColor(Color.parseColor("#ffffff"))
        }


    }

}
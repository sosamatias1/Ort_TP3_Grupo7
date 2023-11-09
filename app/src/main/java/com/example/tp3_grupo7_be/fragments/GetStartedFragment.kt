package com.example.tp3_grupo7_be.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.adapters.ViewPagerAdapter
import com.example.tp3_grupo7_be.fragments.slides.SlideFragment1
import com.example.tp3_grupo7_be.fragments.slides.SlideFragment2
import com.example.tp3_grupo7_be.fragments.slides.SlideFragment3
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class GetStartedFragment : Fragment() {
    lateinit var fragmentList: ArrayList<Fragment>
    lateinit var viewPager: ViewPager2
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_get_started, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val primeraVez = sharedPreferences.getBoolean("primeraVez", true)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        if(primeraVez) {

        fragmentList = arrayListOf<Fragment>(
            SlideFragment1(),
            SlideFragment2(),
            SlideFragment3()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        viewPager = view.findViewById(R.id.viewPager2)

        viewPager.adapter = adapter
        val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
        dotsIndicator.attachTo(viewPager)

        button = view.findViewById(R.id.button2)

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position ==2) {
                    button.visibility = View.VISIBLE
                } else {
                    button.visibility = View.GONE
                }
            }

        })


        button.setOnClickListener {

            val loginFragment = LoginFragment()
            transaction.replace(R.id.fragmentContainerView, loginFragment)
            transaction.commit()

        }

            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()

        } else {
            transaction.replace(R.id.fragmentContainerView, LoginFragment())
        }

    }


}
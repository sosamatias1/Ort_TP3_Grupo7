package com.example.tp3_grupo7_be

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tp3_grupo7_be.models.Perro


class DogDetailFragment : Fragment() {

    lateinit var v: View
    lateinit var text: TextView
    lateinit var perro: Perro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dog_detail, container, false)
       text = v.findViewById(R.id.dogDetail_name)
        return v
    }

    override fun onStart() {
        super.onStart()

        arguments?.let {
            perro = DogDetailFragmentArgs.fromBundle(it).argsDogDetail
            text.text = perro.nombre
        }

        val imageList = ArrayList<SlideModel>() // Create image list

        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(perro.imagen[0]))

        val imageSlider = v.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}
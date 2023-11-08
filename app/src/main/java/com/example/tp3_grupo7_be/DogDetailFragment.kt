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
    lateinit var perro: Perro
    lateinit var nombrePerro: TextView
    lateinit var edadPerro: TextView
    lateinit var provinciaPerro: TextView
    lateinit var generoPerro: TextView
    lateinit var pesoPerro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dog_detail, container, false)
        nombrePerro = v.findViewById(R.id.dogDetail_name)
        edadPerro = v.findViewById(R.id.dogDetail_age)
        provinciaPerro = v.findViewById(R.id.dogDetail_provincia)
        generoPerro = v.findViewById(R.id.dogDetail_gender)
        pesoPerro = v.findViewById(R.id.dogDetail_peso)
        return v
    }

    override fun onStart() {
        super.onStart()

        arguments?.let {
            perro = DogDetailFragmentArgs.fromBundle(it).argsDogDetail
            nombrePerro.text = perro.nombre
            edadPerro.text = perro.edad.toString()
            provinciaPerro.text = perro.provincia
            generoPerro.text = perro.genero
            pesoPerro.text = perro.peso.toString()
        }

        val imageList = ArrayList<SlideModel>()
        for (i in 0..<perro.imagen.size) {
            imageList.add(SlideModel(perro.imagen[i]))
        }

        val imageSlider = v.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}
package com.example.tp3_grupo7_be

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.models.Perro
import kotlinx.coroutines.launch


class DogDetailFragment : Fragment() {

    lateinit var v: View
    lateinit var perro: Perro
    private var db: appDatabase? = null
    private var perroDao: perroDao? = null
    lateinit var nombrePerro: TextView
    lateinit var edadPerro: TextView
    lateinit var provinciaPerro: TextView
    lateinit var generoPerro: TextView
    lateinit var pesoPerro: TextView
    lateinit var botonAdopcion: Button

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
        botonAdopcion = v.findViewById(R.id.adoptar_btn)

        return v
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()

        val context = view?.context
        if (context != null) {
            db = appDatabase.getAppDataBase(context)
        }
        perroDao = db?.perroDao()

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

        onClickedButtonAdoption()

    }

    private fun onClickedButtonAdoption(){
        botonAdopcion.setOnClickListener {
            lifecycleScope.launch {
                val filasActualizadas = perroDao?.updateAdoptadoPerro(perro.id)
                Log.d("Debug", "Filas actualizadas: $filasActualizadas")
            }
        }
    }
}
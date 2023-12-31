package com.example.tp3_grupo7_be.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.database.adoptadoDao
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.models.Adoptado
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.views.viewmodels.SharedViewModel
import kotlinx.coroutines.launch


class DogDetailFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var v: View
    lateinit var perro: Perro
    lateinit var adoptado: Adoptado
    private var db: appDatabase? = null
    private var perroDao: perroDao? = null
    private var adoptadoDao: adoptadoDao? = null
    lateinit var nombrePerro: TextView
    lateinit var edadPerro: TextView
    lateinit var adoptadoTexto: TextView
    lateinit var tv_adoptante: TextView
    lateinit var provinciaPerro: TextView
    lateinit var generoPerro: TextView
    lateinit var pesoPerro: TextView
    lateinit var botonAdopcion: Button
    lateinit var duenio: TextView
    lateinit var adoptante: String
    lateinit var telefono: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle_perro, container, false)
        nombrePerro = v.findViewById(R.id.dogDetail_name)
        edadPerro = v.findViewById(R.id.dogDetail_age)
        provinciaPerro = v.findViewById(R.id.dogDetail_provincia)
        generoPerro = v.findViewById(R.id.dogDetail_gender)
        pesoPerro = v.findViewById(R.id.dogDetail_weight)
        botonAdopcion = v.findViewById(R.id.adoptar_btn)
        duenio = v.findViewById(R.id.tv_detail_duenio)
        tv_adoptante = v.findViewById(R.id.adoptante_detalle)
        adoptadoTexto= v.findViewById(R.id.adoptante_texto_detalle)
        telefono= v.findViewById(R.id.duenio_layout_phone)


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
        adoptadoDao = db?.adoptadoDao()

        arguments?.let {
            perro = DogDetailFragmentArgs.fromBundle(it).argsDogDetail
            nombrePerro.text = perro.nombre
            edadPerro.text = "Edad: " + perro.edad.toString()
            provinciaPerro.text = perro.provincia
            generoPerro.text = perro.genero
            pesoPerro.text = perro.peso.toString()
            duenio.text = perro.nombreDuenio
        }

        val imageList = ArrayList<SlideModel>()
        for (i in 0..<perro.imagen.size) {
            imageList.add(SlideModel(perro.imagen[i]))
        }

        val imageSlider = v.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        estadoAdoptado()
        onClickedButtonAdoption()
        onClickedButtonPhone()
    }

    private fun onClickedButtonPhone() {
        telefono.setOnClickListener {
            val numero = "1234567890"
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$numero")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun onClickedButtonAdoption(){
        botonAdopcion.setOnClickListener {
            try{
            lifecycleScope.launch {
                sharedViewModel.username.observe(viewLifecycleOwner, Observer { username ->
                    adoptante = username
                })
                val filasActualizadas = perroDao?.updateAdoptadoPerro(perro.id, adoptante)
                Log.d("Debug", "Filas actualizadas: $filasActualizadas")
                adoptado = Adoptado(perro.id, adoptante)
                adoptadoDao?.insertAdoptado(adoptado)
                val adoptadoDB = adoptadoDao?.loadAdoptadosById(adoptado.idPerro)
                if (adoptadoDB != null) {
                    Log.d("DebugAdoptados", "Id adoptadoDB: ${adoptadoDB.idPerro} + Adoptado: ${adoptado.idPerro}")
                }

            }
                Toast.makeText(context, "Adoptaste a " + perro.nombre, Toast.LENGTH_SHORT).show()
                val action =
                    DogDetailFragmentDirections.actionDogDetailFragmentToHomeFragment()
                this.findNavController().navigate(action)
            } catch(e: Error) {
                Toast.makeText(context, "No se pudo completar la solicitud de adopción", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun estadoAdoptado(){
        if (perro.adoptado){
            tv_adoptante.text = perro.nombreAdoptante
            tv_adoptante.visibility = View.VISIBLE
            botonAdopcion.isEnabled = false
            botonAdopcion.text = "Adoptado"
            adoptadoTexto.visibility = View.VISIBLE

        }
    }
}
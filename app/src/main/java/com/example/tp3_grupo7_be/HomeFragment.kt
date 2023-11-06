package com.example.tp3_grupo7_be

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.service.ActivityServiceApiBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), AdaptadorClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var listaDePerros: MutableList<Perro> = ArrayList()
    var listaDeImagenes: MutableList<String> = ArrayList()


    private var db: appDatabase? = null
    private var perroDao: perroDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_home)



        return view
    }


    override fun onStart() {
        super.onStart()
        val resultado = loadImagenes()
        lifecycleScope.launch {
            resultado.await()
            cargarDB()
            initRecyclerView()
            // loadPerroRecycler()
        }
        val context = view?.context
        if (context != null) {
            db = appDatabase.getAppDataBase(context)
        }
        perroDao = db?.perroDao()



    }

    fun initRecyclerView(){

        requireActivity()
        recyclerView.setHasFixedSize(true)
        listaDePerros = perroDao?.loadAllPerrosNoAdoptados()!!
        adapter = PerrosAdapter(listaDePerros)
        adapter.setClickListener(this)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

    }

    fun loadImagenes(): Deferred<Unit> {
        val service = ActivityServiceApiBuilder.create()

        return GlobalScope.async(Dispatchers.IO) {
            val response = service.getImagenes().execute()

            if (response.isSuccessful) {
                val responseImagenes = response.body()
                val imagenes = responseImagenes?.imagenes ?: emptyList()
                listaDeImagenes.clear()
                listaDeImagenes.addAll(imagenes)
            } else {
                println("Error con el loadImagenes")
            }
        }
    }

    fun loadPerroRecycler(){
        for (i in 1..10) {

            val imagen: String = listaDeImagenes.get(i)
            listaDePerros.add(Perro("Perro$i", imagen,"Raza$i", "SubRaza$i", true, Perro.Provincias.BUENOS_AIRES.toString(), false))
        }
    }

    fun cargarDB(){

    if (perroDao?.loadAllPerrosNoAdoptados()!!.isEmpty()){

        perroDao?.insertPerro(Perro("Perro1", "https://images.dog.ceo/breeds/terrier-wheaten/n02098105_2945.jpg", "Raza1", "SubRaza1", true, Perro.Provincias.BUENOS_AIRES, false))
        perroDao?.insertPerro(Perro("Perro2", "https://images.dog.ceo/breeds/terrier-bedlington/n02093647_3215.jpg", "Raza2", "SubRaza2", true, Perro.Provincias.BUENOS_AIRES, false))
        perroDao?.insertPerro(Perro("Perro3", "https://images.dog.ceo/breeds/akita/An_Akita_Inu_resting.jpg", "Raza3", "SubRaza3", false, Perro.Provincias.CORDOBA, false))
        perroDao?.insertPerro(Perro("Perro4", "https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_1523.jpg", "Raza4", "SubRaza4", false, Perro.Provincias.CORDOBA, false))
        perroDao?.insertPerro(Perro("Perro5", "https://images.dog.ceo/breeds/rottweiler/n02106550_4987.jpg", "Raza5", "SubRaza5", false, Perro.Provincias.SANTA_FE, false))
        perroDao?.insertPerro(Perro("Perro6", "https://images.dog.ceo/breeds/stbernard/n02109525_5013.jpg", "Raza6", "SubRaza6", false, Perro.Provincias.SANTA_FE, false))
        perroDao?.insertPerro(Perro("Perro7", "https://images.dog.ceo/breeds/corgi-cardigan/n02113186_8794.jpg", "Raza7", "SubRaza7", false, Perro.Provincias.BUENOS_AIRES, false))
    }
        }


    override fun onCheckBoxCheckedChange(perro: Perro, isChecked: Boolean) {
        // Realiza la actualización en la base de datos desde el fragmento
        // Asegúrate de usar coroutines si es necesario
        lifecycleScope.launch {
            val filasActualizadas = perroDao?.updateFavoritoPerro(isChecked, perro.id)
            Log.d("Debug", "Filas actualizadas: $filasActualizadas")
        }
    }

}

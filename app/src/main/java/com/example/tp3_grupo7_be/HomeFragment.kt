package com.example.tp3_grupo7_be

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
        recyclerView.adapter = adapter
        adapter.setClickListener(this)
        linearLayoutManager = LinearLayoutManager(context)
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
    fun cargarDB(){
    if (perroDao?.loadAllPerrosNoAdoptados()!!.isEmpty()){
        var lista1: MutableList<String> = ArrayList()
        var lista2: MutableList<String> = ArrayList()
        var lista3: MutableList<String> = ArrayList()
        var lista4: MutableList<String> = ArrayList()
        var lista5: MutableList<String> = ArrayList()
        var lista6: MutableList<String> = ArrayList()
        var lista7: MutableList<String> = ArrayList()

        lista1.add("https://images.dog.ceo/breeds/terrier-wheaten/n02098105_2945.jpg")
        lista1.add("https://images.dog.ceo/breeds/terrier-wheaten/clementine.jpg")
        lista2.add("https://images.dog.ceo/breeds/terrier-bedlington/n02093647_3215.jpg")
        lista2.add("https://images.dog.ceo/breeds/terrier-bedlington/n02093647_1022.jpg")
        lista3.add("https://images.dog.ceo/breeds/akita/An_Akita_Inu_resting.jpg")
        lista3.add("https://images.dog.ceo/breeds/akita/512px-Ainu-Dog.jpg")
        lista4.add("https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_1523.jpg")
        lista4.add("https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_1024.jpg")
        lista5.add("https://images.dog.ceo/breeds/rottweiler/n02106550_4987.jpg")
        lista6.add("https://images.dog.ceo/breeds/stbernard/n02109525_5013.jpg")
        lista7.add("https://images.dog.ceo/breeds/corgi-cardigan/n02113186_8794.jpg")

        perroDao?.insertPerro(Perro("Perro1", lista1, "Raza1", "SubRaza1", true, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO, "Dueño1", 20))
        perroDao?.insertPerro(Perro("Perro2", lista2, "Raza2", "SubRaza2", true, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO, "Dueño2", 20))
        perroDao?.insertPerro(Perro("Perro3", lista3, "Raza3", "SubRaza3", false, Perro.Provincias.CORDOBA, false, 3, Perro.Generos.MACHO, "Dueño3", 20))
        perroDao?.insertPerro(Perro("Perro4", lista4, "Raza4", "SubRaza4", false, Perro.Provincias.CORDOBA, false, 3, Perro.Generos.MACHO, "Dueño4", 20))
        perroDao?.insertPerro(Perro("Perro5", lista5, "Raza5", "SubRaza5", false, Perro.Provincias.SANTA_FE, false, 3, Perro.Generos.MACHO, "Dueño5", 20))
        perroDao?.insertPerro(Perro("Perro6", lista6, "Raza6", "SubRaza6", false, Perro.Provincias.SANTA_FE, false, 3, Perro.Generos.MACHO, "Dueño6", 20))
        perroDao?.insertPerro(Perro("Perro7", lista7, "Raza7", "SubRaza7", false, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO, "Dueño7", 20))
        perroDao?.insertPerro(Perro("Perro8", lista6, "Raza8", "SubRaza8", false, Perro.Provincias.SANTA_FE, true, 3, Perro.Generos.MACHO, "Dueño8", 20))
        perroDao?.insertPerro(Perro("Perro9", lista7, "Raza9", "SubRaza9", false, Perro.Provincias.BUENOS_AIRES, true, 3, Perro.Generos.MACHO, "Dueño9", 20))
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

    override fun onViewItemDetail(perro: Perro) {
        val action = HomeFragmentDirections.actionHomeFragmentToDogDetailFragment(perro)
        this.findNavController().navigate(action)
    }


}

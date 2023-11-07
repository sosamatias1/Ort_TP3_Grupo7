package com.example.tp3_grupo7_be

import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.holders.PerrosHolder
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.service.ActivityServiceApiBuilder
import com.example.tp3_grupo7_be.service.ImagenPerroRespuesta
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), AdaptadorClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchView: SearchView
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
        searchView = view.findViewById(R.id.buscador)




        return view
    }


    override fun onStart() {
        super.onStart()
        val resultado = loadImagenes()
        lifecycleScope.launch {

            resultado.await()
            cargarDB()
            initRecyclerView()
            initSearchView()
            // loadPerroRecycler()
        }
        val context = view?.context
        if (context != null) {
            db = appDatabase.getAppDataBase(context)
        }
        perroDao = db?.perroDao()



    }



    private fun initSearchView() {
        searchView.clearFocus()

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(raza: String): Boolean {
                filterList(raza)
                return true
            }

        } )
    }
    private fun filterList(text: String) {
        val listaFiltrada: MutableList<Perro> = ArrayList()
        for (perro in perroDao?.loadAllPerrosNoAdoptados()!!) {
                if(perro.raza.lowercase().contains(text.lowercase()) || perro.subRaza.lowercase().contains(text.lowercase())) {
                    listaFiltrada.add(perro)
                }
            adapter.setFilteredList(listaFiltrada)
        }

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

        perroDao?.insertPerro(Perro("Perro1", "https://images.dog.ceo/breeds/terrier-wheaten/n02098105_2945.jpg", "Raza1", "SubRaza1", true, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro2", "https://images.dog.ceo/breeds/terrier-bedlington/n02093647_3215.jpg", "Raza2", "SubRaza2", true, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro3", "https://images.dog.ceo/breeds/akita/An_Akita_Inu_resting.jpg", "Raza3", "SubRaza3", false, Perro.Provincias.CORDOBA, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro4", "https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_1523.jpg", "Raza4", "SubRaza4", false, Perro.Provincias.CORDOBA, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro5", "https://images.dog.ceo/breeds/rottweiler/n02106550_4987.jpg", "Raza5", "SubRaza5", false, Perro.Provincias.SANTA_FE, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro6", "https://images.dog.ceo/breeds/stbernard/n02109525_5013.jpg", "Raza6", "SubRaza6", false, Perro.Provincias.SANTA_FE, false, 3, Perro.Generos.MACHO))
        perroDao?.insertPerro(Perro("Perro7", "https://images.dog.ceo/breeds/corgi-cardigan/n02113186_8794.jpg", "Raza7", "SubRaza7", false, Perro.Provincias.BUENOS_AIRES, false, 3, Perro.Generos.MACHO))
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

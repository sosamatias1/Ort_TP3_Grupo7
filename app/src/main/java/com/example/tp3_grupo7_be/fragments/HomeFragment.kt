package com.example.tp3_grupo7_be.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.adapters.FiltrosAdapter
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.database.adoptadoDao
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.service.ActivityServiceApiBuilder
import com.example.tp3_grupo7_be.service.ImagenPerroRespuesta
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random


class HomeFragment : Fragment(), AdaptadorClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter


    lateinit var recyclerViewFiltros: RecyclerView

    lateinit var barraProgreso: ProgressBar
    lateinit var adapterFiltros: FiltrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchView: SearchView
    var listaDePerros: MutableList<Perro> = ArrayList()
    var listaDeImagenes: MutableList<String> = ArrayList()
    lateinit var listaDeRazas: Map<String, MutableList<String>>

    private val razas: List<String> = listOf(
        "terrier", "retriever", "akita", "retriever", "rottweiler",
        "stbernard", "corgi", "beagle", "husky", "retriever"
    )

    private val subrazas: List<String?> = listOf(
        "wheaten", "flatcoated", null, "chesapeake", null,
        null, "cardigan", null, null, "golden"
    )

    private val nombresDuenios: List<String?> = listOf(
        "Carlos","Martina","Diego","Valentina","Juan",
        "Sofía","Camila","Francisco","Jorge","Matías"
    )

    private val nombres: List<String?> = listOf(
        "Pancho", "Negra", "Toto", "Lulu", "Cachito",
        "Sultan", "Rocco", "Leia", "Jorge Junior", "Roman"
    )




    private var db: appDatabase? = null
    private var perroDao: perroDao? = null

    private var adoptadoDao: adoptadoDao? = null

    private var filtroSeleccionado: String? = null

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
        recyclerViewFiltros = view.findViewById(R.id.recycler_filtro)
        barraProgreso = view.findViewById(R.id.barra_progreso_home)
        return view
    }


    override fun onStart() {
        super.onStart()
        barraProgreso.visibility = View.VISIBLE
        val resultado = loadImagenes("retriever", "golden")
        lifecycleScope.launch {

            resultado.await()
            cargarDB()
            barraProgreso.visibility = View.GONE
            initRecyclerViewFiltros()
            initRecyclerView()
            initSearchView()
            // loadPerroRecycler()
        }
        val context = view?.context
        if (context != null) {
            db = appDatabase.getAppDataBase(context)
        }

        perroDao = db?.perroDao()
        adoptadoDao = db?.adoptadoDao()



    }

    override fun onPause() {
        super.onPause()
        clearSearchViewText()
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

            if (text.isEmpty()) {
                if (filtroSeleccionado == null || perro.raza == filtroSeleccionado) {
                    listaFiltrada.add(perro)
                }
            } else {
                if ((perro.raza.lowercase().contains(text.lowercase()) || perro.subRaza.lowercase().contains(text.lowercase()) || perro.edad.toString().contains(text.lowercase())) && (filtroSeleccionado == null || perro.raza == filtroSeleccionado)) {
                    listaFiltrada.add(perro)
                }
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

    fun initRecyclerViewFiltros() {
        var filtros: MutableList<String>
        filtros = perroDao?.loadAllRazas() ?: emptyList<String>() as MutableList<String>


        recyclerViewFiltros.setHasFixedSize(true)
        adapterFiltros = FiltrosAdapter(filtros)
        recyclerViewFiltros.adapter = adapterFiltros
        adapterFiltros.setClickListener(this)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFiltros.layoutManager = linearLayoutManager


    }

    override fun onFilterSelected(filtro: String) {
        filtroSeleccionado = filtro
        var listaFiltrada = listaDePerros.filter { it.raza == filtroSeleccionado} as MutableList
    if(searchView.query.isNotEmpty()) {
        listaFiltrada =
            listaDePerros.filter { it.raza == filtroSeleccionado && it.edad.toString() == searchView.query.toString() } as MutableList
    }
        adapter.setFilteredList(listaFiltrada)


    }

    override fun onFilterRemoved() {
        filtroSeleccionado = null
        var listaFiltrada = listaDePerros
        if(searchView.query.isNotEmpty()) {
            listaFiltrada =
                listaDePerros.filter { it.edad.toString() == searchView.query.toString() || it.raza == searchView.query.toString() || it.subRaza == searchView.query.toString()} as MutableList
        }
        // adapter.setFilteredList(listaDePerros)
        adapter.setFilteredList(listaFiltrada)
    }


    fun loadImagenes(raza: String, subraza: String?): Deferred<Unit> {
        val service = ActivityServiceApiBuilder.create()

        return GlobalScope.async(Dispatchers.IO) {
            var response: Response<ImagenPerroRespuesta>
            if (subraza != null) {
                response = service.getImagenes(raza, subraza).execute()
            } else {
                response = service.getImagenes(raza).execute()
            }
            if (response.isSuccessful) {
                val responseImagenes = response.body()
                val imagenes = responseImagenes?.imagenes ?: emptyList()
                listaDeImagenes.clear()
                for (i in 1..4) {
                    listaDeImagenes.add(imagenes[i])
                }
            } else {
                println("Error con el loadImagenes")
            }
        }
    }


    suspend fun cargarDB() {
        if (perroDao?.loadAllPerrosNoAdoptados()!!.isEmpty()) {
            var razaTemporal: String
            var subrazaTemporal: String?

            for (i in 0..9) {
                razaTemporal = razas.get(i)
                subrazaTemporal = subrazas.get(i)
                loadImagenes(razaTemporal, subrazaTemporal).await()

                perroDao?.insertPerro(
                    Perro(
                        nombres.get(i),
                        listaDeImagenes,
                        razaTemporal,
                        subrazaTemporal,
                        Perro.Provincias.values().random().provincia,
                        Random.nextInt(3),
                        Perro.Generos.values().random().genero,
                        nombresDuenios.get(i),
                        20
                    )
                )
            }
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
        val action =
            HomeFragmentDirections.actionHomeFragmentToDogDetailFragment(
                perro
            )
        this.findNavController().navigate(action)
    }

    private fun clearSearchViewText() {
        searchView.setQuery("", false)
    }

}

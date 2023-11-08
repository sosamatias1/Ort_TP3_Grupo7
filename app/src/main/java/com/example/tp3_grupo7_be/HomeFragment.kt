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
import com.example.tp3_grupo7_be.service.ImagenPerroRespuesta
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment(), AdaptadorClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var listaDePerros: MutableList<Perro> = ArrayList()
    var listaDeImagenes: MutableList<String> = ArrayList()
    lateinit var listaDeRazas: Map<String, MutableList<String>>

    private val razas: List<String> = listOf(
        "terrier", "terrier", "akita", "retriever", "rottweiler",
        "stbernard", "corgi", "beagle", "husky", "retriever"
    )

    private val subrazas: List<String?> = listOf(
        "wheaten", "bedlington", null, "chesapeake", null,
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
        val resultado = loadImagenes("retriever", "golden")
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

    fun initRecyclerView() {

        requireActivity()
        recyclerView.setHasFixedSize(true)
        listaDePerros = perroDao?.loadAllPerrosNoAdoptados()!!
        adapter = PerrosAdapter(listaDePerros)
        recyclerView.adapter = adapter
        adapter.setClickListener(this)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

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
                        3,
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
        val action = HomeFragmentDirections.actionHomeFragmentToDogDetailFragment(perro)
        this.findNavController().navigate(action)
    }


}

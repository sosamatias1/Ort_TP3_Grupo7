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
                for (i in 1 .. 4){

                listaDeImagenes.add(imagenes[i])
                }

            } else {
                println("Error con el loadImagenes")
            }
        }
    }


    suspend fun cargarDB() {
        if (perroDao?.loadAllPerrosNoAdoptados()!!.isEmpty()) {
            var raza1 = "terrier";
            var subraza1= "wheaten"
            var raza2 = "terrier";
            var subraza2= "bedlington"
            var raza3= "akita"
            var subraza3= null
            var raza4 = "retriever";
            var subraza4= "chesapeake"
            var raza5 = "rottweiler";
            var subraza5= null
            var raza6 = "stbernard";
            var subraza6= null
            var raza7 = "corgi";
            var subraza7= "cardigan"
            var raza8 = "beagle";
            var subraza8= null
            var raza9 = "husky";
            var subraza9= null
            var raza10 = "retriever";
            var subraza10= "golden"

            loadImagenes(raza1, subraza1).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro1",
                    listaDeImagenes,
                    raza1,
                    subraza1,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño1",
                    20
                )
            )
            loadImagenes(raza2, subraza2).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro2",
                    listaDeImagenes,
                    raza2,
                    subraza2,

                    Perro.Provincias.BUENOS_AIRES.provincia,

                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño2",
                    20
                )
            )
            loadImagenes(raza3, subraza3).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro3",
                    listaDeImagenes,
                    raza3,
                    subraza3,

                    Perro.Provincias.BUENOS_AIRES.provincia,

                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño3",
                    20
                )
            )
            loadImagenes(raza4, subraza4).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro4",
                    listaDeImagenes,
                    raza4,
                    subraza4,

                    Perro.Provincias.BUENOS_AIRES.provincia,

                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño4",
                    20
                )
            )
            loadImagenes(raza5, subraza5).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro5",
                    listaDeImagenes,
                    raza5,
                    subraza5,

                    Perro.Provincias.BUENOS_AIRES.provincia,

                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño5",
                    20
                )
            )
            loadImagenes(raza6, subraza6).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro6",
                    listaDeImagenes,
                    raza6,
                    subraza6,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño6",
                    20
                )
            )
            loadImagenes(raza7, subraza7).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro7",
                    listaDeImagenes,
                    raza7,
                    subraza7,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño7",
                    20
                )
            )
            loadImagenes(raza8, subraza8).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro8",
                    listaDeImagenes,
                    raza8,
                    subraza8,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño8",
                    20
                )
            )
            loadImagenes(raza9, subraza9).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro9",
                    listaDeImagenes,
                    raza9,
                    subraza9,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño9",
                    20
                )
            )
            loadImagenes(raza10, subraza10).await()
            perroDao?.insertPerro(
                Perro(
                    "Perro10",
                    listaDeImagenes,
                    raza10,
                    subraza10,
                    Perro.Provincias.BUENOS_AIRES.provincia,
                    3,
                    Perro.Generos.MACHO.genero,
                    "Dueño10",
                    20
                )
            )
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

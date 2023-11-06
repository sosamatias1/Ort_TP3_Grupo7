package com.example.tp3_grupo7_be

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.listeners.OnViewItemClickedListener
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.service.ActivityServiceApiBuilder
import com.example.tp3_grupo7_be.service.ImagenPerroRespuesta
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), OnViewItemClickedListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var listaDePerros: MutableList<Perro> = ArrayList()
    var listaDeImagenes: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_home)
        return view
    }


    override fun onStart() {
        super.onStart()

        loadImagenes()
        Log.e("prueba", listaDeImagenes.toString())

        initRecyclerView()
    }

    fun initRecyclerView(){
        requireActivity()
        recyclerView.setHasFixedSize(true)
        adapter = PerrosAdapter(listaDePerros, this)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

    }

    fun loadImagenes(){
        val service = ActivityServiceApiBuilder.create()
        service.getImagenes().enqueue(object: Callback<ImagenPerroRespuesta> {
            override fun onResponse(
                call: Call<ImagenPerroRespuesta>,
                response: Response<ImagenPerroRespuesta>
            ) {

                if(response.isSuccessful){
                    val responseImagenes = response.body()
                    val imagenes = responseImagenes?.imagenes ?: emptyList()
                    listaDeImagenes.clear()
                    listaDeImagenes.addAll(imagenes)
                }else{
                    print("Error con el loadImagenes")
                }
                for (i in 1..10) {
                    val imagen: String = listaDeImagenes.get(i)
                    listaDePerros.add(Perro("Perro$i", imagen))
                }
                initRecyclerView()

            }

            override fun onFailure(call: Call<ImagenPerroRespuesta>, t: Throwable) {
                Log.e("error", t.toString())
            }
        })

    }

    override fun onViewItemDetail(perro: Perro) {
        val action = HomeFragmentDirections.actionHomeFragmentToDogDetailFragment(perro)
        this.findNavController().navigate(action)
    }
}

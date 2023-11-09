package com.example.tp3_grupo7_be.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener
import com.example.tp3_grupo7_be.models.Perro
import kotlinx.coroutines.launch


class FavoritosFragment : Fragment(), AdaptadorClickListener {
    private var db: appDatabase? = null
    private var perroDao: perroDao? = null
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

        var view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_favorito)
        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        val context = view?.context
        if (context != null) {
            db = appDatabase.getAppDataBase(context)
        }
        perroDao = db?.perroDao()
        initRecyclerView()

    }

    fun initRecyclerView() {
        requireActivity()
        recyclerView.setHasFixedSize(true)
        listaDePerros = perroDao?.loadAllPerrosNoAdoptadosFavoritos()!!
        adapter = PerrosAdapter(listaDePerros)
        adapter.setClickListener(this)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

    }

    override fun onCheckBoxCheckedChange(perro: Perro, isChecked: Boolean) {
        lifecycleScope.launch {
            val filasActualizadas = perroDao?.updateFavoritoPerro(isChecked, perro.id)
            Log.d("Debug", "Filas actualizadas: $filasActualizadas")
            initRecyclerView()
        }
    }

    override fun onViewItemDetail(perro: Perro) {
        val action =
            FavoritosFragmentDirections.actionFavoritosFragmentToDogDetailFragment(
                perro
            )
        this.findNavController().navigate(action)
    }

    override fun onFilterSelected(filter: String) {

    }

    override fun onFilterRemoved() {

    }
}
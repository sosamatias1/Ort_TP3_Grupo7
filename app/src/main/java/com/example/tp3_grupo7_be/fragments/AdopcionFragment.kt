package com.example.tp3_grupo7_be.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.adapters.PerrosAdapter
import com.example.tp3_grupo7_be.database.appDatabase
import com.example.tp3_grupo7_be.database.perroDao
import com.example.tp3_grupo7_be.models.Perro

class AdopcionFragment : Fragment() {
    private var db: appDatabase? = null
    private var perroDao: perroDao? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PerrosAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var listaDePerros: MutableList<Perro> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_adopcion, container, false)
        recyclerView = view.findViewById(R.id.recycler_adopcion)
        return view
    }

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
        listaDePerros = perroDao?.loadAllPerrosAdoptados()!!
        adapter = PerrosAdapter(listaDePerros)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
    }

}
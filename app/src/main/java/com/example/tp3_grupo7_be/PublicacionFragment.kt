package com.example.tp3_grupo7_be

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.lifecycleScope
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.service.ActivityServiceApiBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PublicacionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PublicacionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var acRazas: AutoCompleteTextView
    lateinit var acProvincias: AutoCompleteTextView
    lateinit var acGeneros: AutoCompleteTextView
    var razas: MutableList<String> = ArrayList()
    var provincias: MutableList<String> = ArrayList()
    var generos: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_publicacion, container, false)
        acRazas = v.findViewById(R.id.ac_raza_publicacion)
        acProvincias = v.findViewById(R.id.ac_ubicacion_publicacion)
        acGeneros = v.findViewById(R.id.ac_genero_publicacion)
        return v
    }

    override fun onStart() {
        super.onStart()

        val resultado = loadRazas()
        lifecycleScope.launch {
            resultado.await()
        }
        cargarRazasAC()
        cargarProvinciasAC()
        cargarGenerosAC()


    }


    fun cargarProvinciasAC(){

        val provinciasCrudo = Perro.Provincias.values()
        var j = 0;
        for (i in provinciasCrudo){
            provincias.add(provinciasCrudo.get(j).provincia)
            j++;
        }
        var adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), R.layout.drop_down_item,
            provincias
        )
        acProvincias.setAdapter(adapter)
    }

    fun cargarGenerosAC(){

        val generosCrudo = Perro.Generos.values()
        var j = 0;
        for (i in generosCrudo){
            generos.add(generosCrudo.get(j).genero)
            j++;
        }
        var adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), R.layout.drop_down_item,
            generos
        )
        acGeneros.setAdapter(adapter)
    }
    fun cargarRazasAC(){
        var adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), R.layout.drop_down_item,
            razas
        )
        acRazas.setAdapter(adapter)
    }
    fun loadRazas(): Deferred<Unit> {
        val service = ActivityServiceApiBuilder.create()

        return GlobalScope.async(Dispatchers.IO) {
            var response = service.getRazas().execute()
            if (response.isSuccessful) {
                val responseRazas = response.body()
                val razasLoad = responseRazas?.razas ?: emptyMap()
                razas.clear()
                for ((raza, subRazas) in razasLoad) {
                    if (subRazas.isEmpty()) {
                        razas.add("$raza - Sin subraza")
                    } else {
                        for (subRaza in subRazas) {
                            val combinacion = "$raza - $subRaza"
                            razas.add(combinacion)
                        }
                    }
                }

            } else {
                println("Error con el loadRazas")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PublicacionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PublicacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
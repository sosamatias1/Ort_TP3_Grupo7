package com.example.tp3_grupo7_be.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.views.MainActivity


class LoginFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val buttonLogin = view.findViewById<Button>(R.id.btnIngresar)
        val nombreEditText = view.findViewById<EditText>(R.id.editTextNombre)



        buttonLogin.setOnClickListener {
            val nombre: String = nombreEditText.text.toString()
            val intent = Intent(activity, MainActivity::class.java)


           if(nombre.isNotEmpty()) {
               intent.putExtra("username", nombre)
               editor.putBoolean("usuarioLogueado", true)
               editor.apply()

               startActivity(intent)
                activity?.finish()
            } else {
                showToast("Por favor ingrese un nombre valido")
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
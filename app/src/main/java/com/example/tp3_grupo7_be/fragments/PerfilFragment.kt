package com.example.tp3_grupo7_be.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tp3_grupo7_be.R
import com.google.android.material.imageview.ShapeableImageView


class PerfilFragment : Fragment() {
    lateinit var btnSubirFoto: Button
    lateinit var fotoPerfil: ShapeableImageView
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        fotoPerfil = view.findViewById(R.id.foto_perfil)
        btnSubirFoto = view.findViewById(R.id.btn_subir_foto)

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                    val data: Intent? = result.data
                    if (data != null)
                        fotoPerfil.setImageURI(data.data)// Puedes acceder a los datos de la imagen seleccionada desde 'data'
                }
            }

        return view
    }

    override fun onStart() {
        super.onStart()

        btnSubirFoto.setOnClickListener{

            val iGallery = Intent(Intent.ACTION_PICK)
            iGallery.type = "image/*"
            galleryLauncher.launch(iGallery)

        }


    }
}
package com.example.tp3_grupo7_be.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tp3_grupo7_be.R
import androidx.lifecycle.Observer
import com.example.tp3_grupo7_be.views.viewmodels.SharedViewModel
import androidx.fragment.app.activityViewModels


class PerfilFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.username.observe(viewLifecycleOwner, Observer { username ->
            val usernameTextView: TextView = view.findViewById(R.id.username)
            usernameTextView.text = username
        })
    }


}
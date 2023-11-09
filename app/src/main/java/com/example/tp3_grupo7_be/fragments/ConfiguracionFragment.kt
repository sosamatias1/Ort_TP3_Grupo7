package com.example.tp3_grupo7_be.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.views.viewmodels.SharedViewModel


class ConfiguracionFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var switchModoOscuro: SwitchCompat
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("modoOscuro", Context.MODE_PRIVATE)
        switchModoOscuro = view.findViewById(R.id.switch_NightMode)
        val modoOscuro = sharedPreferences.getBoolean("modoOscuro", false)
        switchModoOscuro.isChecked = modoOscuro



        switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            editor = sharedPreferences.edit()
            if(isChecked) {
                editor.putBoolean("modoOscuro", true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor.putBoolean("modoOscuro", false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            editor.apply()
        }

    }
    }


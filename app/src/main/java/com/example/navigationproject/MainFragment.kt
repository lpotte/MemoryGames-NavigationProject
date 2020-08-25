package com.example.navigationproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.button2
import kotlinx.android.synthetic.main.fragment_main.button4

class MainFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var userPlayer: Player
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        userPlayer = Player("ettop", 1000)
        var bundle = bundleOf("infoplayer" to userPlayer)
        button2.setOnClickListener{
            //Llama al fragment del juego 1
            navController!!.navigate(R.id.action_mainFragment_to_pairsGame, bundle)
        }
        button4.setOnClickListener{
            //Llama al fragment del juego 2
            navController!!.navigate(R.id.action_mainFragment_to_secuenceGame,bundle)
        }
    }

}
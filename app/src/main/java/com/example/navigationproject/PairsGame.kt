package com.example.navigationproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_pairs_game.*

class PairsGame: Fragment(), OnClickListener {
    lateinit var player: Player
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pairs_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = requireArguments().getParcelable("infoplayer")!!
        Toast.makeText(context, "¡Encuentra las parejas y gana!", Toast.LENGTH_SHORT).show()
        aleatorio()
        for(i in 0..15){
            //Asigna el evento onClick a todas las imageView de la cuadricula
            requireView().findViewById<ImageView>(ids[i]).setOnClickListener(this)
        }
    }
    var puntos = 0 //variable de puntaje global
    var anterior: ImageView? = null //guarda la tarjeta anterior
    var indexant: Int = -1 //Para guardar el indice de la imagen anterior, servirá en la lista ids
    var visitado = mutableListOf<Boolean>(
        false,false,false,false,false,false,false,false,
        false,false,false,false,false,false,false,false
    )// Controla que una imagen sea girada dos veces

    //Lista de las imagenes
    var images = mutableListOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i)

    //Vector de los imageview
    var ids = listOf(
        R.id.imageView0,
        R.id.imageView2,
        R.id.imageView3,
        R.id.imageView4,
        R.id.imageView5,
        R.id.imageView6,
        R.id.imageView7,
        R.id.imageView8,
        R.id.imageView9,
        R.id.imageView10,
        R.id.imageView11,
        R.id.imageView12,
        R.id.imageView13,
        R.id.imageView14,
        R.id.imageView15,
        R.id.imageView16)

    //Vector para controlar que hayan 2 de cada imagen
    var control = mutableListOf<Int>(0,0,0,0,0,0,0,0)

    //aleatorio que asigna los tag de imagenes a cada imagenview
    fun aleatorio(){
        ids.forEach(){
            var i = (0..7).random()
            while(control[i] == 2) {
                i = (0..7).random()
            }
            control[i] = control[i] + 1
            var img: ImageView = requireView().findViewById(it)
            img.tag = i
            img.setImageResource(images[i])
        }
        //esto se hace para que muestre las imagenes por dos segundos y luego las oculte
        Handler(Looper.getMainLooper()).postDelayed({
            ocultar()
        }, 2000)
    }

    @Override
    override fun onClick(v: View){
        onClickgirar(v)
        imageView17.setOnClickListener(){
            restart(v)
        }
    }
    //Esta función contiene los listeners por cada imagen
    fun onClickgirar(v: View){
        val iv: ImageView = v as ImageView
        iv.setImageResource(images[iv.tag as Int])
        Handler(Looper.getMainLooper()).postDelayed({
            verificarPares(iv, ids.indexOf(iv.id))
        }, 1000)
    }

    //Verifica las parejas
    fun verificarPares (img: ImageView, j: Int){
        if (visitado[j]==false){
            visitado[j]=true
            if(anterior==null){
                anterior = img
                indexant = j
            }else{
                if(anterior!!.tag == img.tag){
                    puntos = puntos + 100
                    puntaje.setText(puntos.toString())
                }else{
                    visitado[j]=false
                    visitado[indexant] = false
                    anterior!!.setImageResource(R.drawable.interrogante)
                    img.setImageResource(R.drawable.interrogante)
                    if(puntos > 20){
                        puntos = puntos - 20
                        puntaje.setText(puntos.toString())
                    }
                }
                anterior = null
                indexant = -1
            }
        }
    }

    //Cambia las imagenes por el signo incognito
    fun ocultar(){
        for (i in 0..15){
            requireView().findViewById<ImageView>(ids[i]).setImageResource(R.drawable.interrogante)
        }
    }

    //Reestablece a cero el puntaje, pone el interrogante en lo imageview y aleatorea los tags
    fun restart (v: View){
        ocultar()
        for (i in 0..7){
            control[i] = 0
        }
        aleatorio()
        puntos = 0
        puntaje.setText("00")
        for (i in 0..15){
            visitado[i] = false
        }
    }

}
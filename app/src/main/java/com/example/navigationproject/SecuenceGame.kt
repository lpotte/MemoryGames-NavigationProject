package com.example.navigationproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_pairs_game.*

class SecuenceGame: Fragment(), View.OnClickListener {
    lateinit var player: Player
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
    var tamSec = 3
    var level = 1
    var secuece = ""
    var puntos = 0
    var visitado = ""// Controla que una imagen sea girada dos veces
    var maxPuntaje = 0
    var ciclos = 0
    var lose = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_secuence_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "Sigue las secuencias que se van mostrando, para ganar puntos", Toast.LENGTH_SHORT).show()
        player = requireArguments().getParcelable("infoplayer")!!
        resetTags()
        aleatorio()
        for(i in 0..15){
            //Asigna el evento onClick a todas las imageView de la cuadricula
            requireView().findViewById<ImageView>(ids[i]).setOnClickListener(this)
        }
    }


    override fun onClick(v: View){
        onClickgirar(v)
        imageView17.setOnClickListener(){
            restart()
        }
    }

    //Genera las secuencias de forma aleatoria
    fun aleatorio(){
        var aux = 0
        while (aux < tamSec){
            var i = (1..16).random()
            while(secuece.contains(i.toString())) {
                i = (1..16).random()
            }
            secuece += i.toString()
            var it = ids[i-1]
            var img: ImageView = requireView().findViewById(it)
            img.tag = i
            img.setImageResource(R.drawable.rosquillas)
            aux++
        }

        //esto se hace para que muestre las imagenes por dos segundos y luego las oculte
        Handler(Looper.getMainLooper()).postDelayed({
            ocultar()
        }, 800)
    }

    fun onClickgirar(v: View){
        val iv: ImageView = v as ImageView
        var tag = iv.tag as Int
        if (!visitado.contains(tag.toString()) && lose != 1) {
            verificarSecuencia(tag.toString(), iv)
        } else {
            if(lose == 1){
                var msm = "Alcanzaste un puntaje de $puntos, ¡Felciitaciones!"
                Toast.makeText(this.context, msm,Toast.LENGTH_LONG).show()
            }
        }
    }

    //Verfifica la secuencia
    fun verificarSecuencia(tagS: String, iv: ImageView){
        if(secuece.contains(tagS)){
            iv.setImageResource(R.drawable.rosquillas)
            puntos += 100
            puntaje.text = puntos.toString()
            Handler(Looper.getMainLooper()).postDelayed({
                visitado += tagS
                levelUp() //Aumenta el nivel de dificultad
            }, 250)
        }else{
            lose = 1
            Toast.makeText(this.context, "Alcanzaste un puntaje de $puntos",Toast.LENGTH_LONG).show()
        }
    }

    //Restablece la secuencia para un proximo nivel
    fun levelUp(){
        if(visitado.length == secuece.length){
            secuece = ""
            visitado = ""
            ocultar()
            resetTags()
            ciclos++
            if(ciclos > 4 && tamSec < 9){
                tamSec++ //Esto aumenta el tamaño de la secuencia
                ciclos = 0
            }
            aleatorio()
        }
    }

    //Cambia las imagenes por el signo incognito
    fun ocultar(){
        for (i in 0..15){
            requireView().findViewById<ImageView>(ids[i]).setImageResource(R.drawable.fondo_negro)
        }
    }

    //reinicia los tags a cero tags
    fun resetTags(){
        for (j in 0..15){
            requireView().findViewById<ImageView>(ids[j]).tag = -1
        }
    }

    //Reestablece a cero el puntaje, pone el interrogante en lo imageview y aleatorea los tags
    fun restart (){
        tamSec = 3
        level = 1
        secuece = ""
        puntos = 0
        visitado = ""// Controla que una imagen sea girada dos veces
        maxPuntaje = 0
        ciclos = 0
        lose = 0
        resetTags()
        ocultar()
        aleatorio()
        puntaje.text = puntos.toString()
    }

}
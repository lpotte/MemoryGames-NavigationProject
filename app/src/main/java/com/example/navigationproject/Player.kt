package com.example.navigationproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Player (var nickname: String, var puntaje:Int): Parcelable{
}
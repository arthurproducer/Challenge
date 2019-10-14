package br.com.arthur.challengelooke.model

import org.parceler.Parcel

@Parcel
data class Looke(
    var name: String = "",
    var bg: String = "",
    var im : String = "",
    var sg : String = ""
)
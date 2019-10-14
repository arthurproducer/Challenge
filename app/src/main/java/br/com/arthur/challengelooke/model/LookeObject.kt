package br.com.arthur.challengelooke.model

import com.google.gson.annotations.SerializedName

data class LookeObject(
    @SerializedName("objects")
    var files : List<Looke> = emptyList()
)
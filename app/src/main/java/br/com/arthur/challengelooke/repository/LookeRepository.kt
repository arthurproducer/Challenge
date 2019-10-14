package br.com.arthur.challengelooke.repository

import br.com.arthur.challengelooke.model.Looke

interface LookeRepository{

    fun loadLookeGson(): List<Looke>?
}
package br.com.arthur.challengelooke.view.challengeDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.repository.LookeRepository
import br.com.arthur.challengelooke.repository.LookeRepositoryImpl

class ChallengeDetailsViewModel(val lookeRepository: LookeRepository): ViewModel(){

    val listLooke = MutableLiveData<List<Looke>>()

    fun getListLooke(){
        listLooke.value = lookeRepository.loadLookeGson()
    }

}
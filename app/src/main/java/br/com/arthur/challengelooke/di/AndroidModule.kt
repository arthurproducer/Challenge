package br.com.arthur.challengelooke.di

import android.content.Context
import android.net.ConnectivityManager
import br.com.arthur.challengelooke.common.LookeHttp
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.model.LookeObject
import br.com.arthur.challengelooke.repository.LookeRepository
import br.com.arthur.challengelooke.repository.LookeRepositoryImpl
import br.com.arthur.challengelooke.view.challengeDetails.ChallengeDetailsViewModel
import br.com.arthur.challengelooke.view.challengeList.ChallengeListViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val repositoryModule = module{
    single<LookeRepository>{LookeRepositoryImpl()}
}

val viewModelModule = module{
    //viewModel { ChallengeListViewModel(get()) }
    viewModel { ChallengeDetailsViewModel(get()) }
}


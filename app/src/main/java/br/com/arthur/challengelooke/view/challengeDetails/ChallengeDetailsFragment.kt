package br.com.arthur.challengelooke.view.challengeDetails


import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.lifecycle.Observer

import br.com.arthur.challengelooke.R
import br.com.arthur.challengelooke.model.Looke
import kotlinx.android.synthetic.main.fragment_challenge_details.*
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.parceler.Parcels
import kotlin.coroutines.CoroutineContext

class ChallengeDetailsFragment : Fragment(), CoroutineScope {

    private lateinit var job : Job
    private var downloadJob : Job? = null
    private lateinit var  mediaPlayer : MediaPlayer

    val lookeListViewModel: ChallengeDetailsViewModel by viewModel()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        job = Job()

        lookeListViewModel.getListLooke()

        lookeListViewModel.listLooke.observe(this, Observer {
            if(it == null){
                Toast.makeText(context, "Não foi possível baixar os dados com MVVM", Toast.LENGTH_SHORT).show()
            }else{
                it.forEach { looke->
                    val videoUri = Uri.parse(looke.bg)
                    videoLooke.setVideoURI(videoUri)
                    val controller = MediaController(context)


                    btnPlay.setOnClickListener {
                        Toast.makeText(context, "Iniciado exibição do video", Toast.LENGTH_SHORT).show()
                        carregarTarefas(
                            looke.sg,
                            controller
                        )
                    }

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

         //val looke = Parcels.unwrap<Looke?>(.getParcelable("looke"))

    }


    private fun showProgress(show: Boolean) {
        if (show) {
            txtLoadingMessage.setText("Carregando video...")
        }
        txtLoadingMessage.visibility = if (show) View.VISIBLE else View.GONE
        pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun playMusic(sg : String){

        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(sg)
            prepare() // might take long! (for buffering, etc)
            start()
        }
    }

    suspend fun playVideo(controller: MediaController){
        videoLooke.setMediaController(controller)
        controller.setMediaPlayer(videoLooke)
        controller.setAnchorView(videoLooke)
    }

    private fun carregarTarefas(sg: String,controller: MediaController){
        launch{
            coroutineScope{
                //val a = async(Dispatchers.IO){playMusic(sg)}
                showProgress(true)
                async(Dispatchers.Default){playVideo(controller)}
                //Log.d("LOOKE","${a.await()} ${b.await()}")
            }
        }
    }

}

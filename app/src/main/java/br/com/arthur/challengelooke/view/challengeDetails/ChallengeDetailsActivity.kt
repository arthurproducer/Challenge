package br.com.arthur.challengelooke.view.challengeDetails

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import br.com.arthur.challengelooke.R
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.view.challengeList.ChallengeListFragment
import kotlinx.android.synthetic.main.activity_challenge_details.*
import kotlinx.android.synthetic.main.fragment_challenge_details.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import org.parceler.Parcels
import kotlin.coroutines.CoroutineContext

class ChallengeDetailsActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job : Job
    private var downloadJob : Job? = null
    private lateinit var  mediaPlayer : MediaPlayer

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_details)

        job = Job()

        val looke = Parcels.unwrap<Looke?>(intent.getParcelableExtra("looke"))

        val videoUri = Uri.parse(looke?.bg)
        videoLooke.setVideoURI(videoUri)
        val controller = MediaController(this)

        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(looke?.sg)
        }

        carregarTarefas(looke!!.sg, controller)

//        if(downloadJob == null){
//            showProgress(false)
//            Log.d("DOWNLOADJOB", "show FALSE")
//            btnPlay.visibility = View.VISIBLE
//        }else if(downloadJob?.isActive == true){
//            showProgress(true)
//            Log.d("DOWNLOADJOB", "show TRUE")
//        }


        btnPlay.setOnClickListener {
            Toast.makeText(this, "Iniciado exibição do video", Toast.LENGTH_SHORT).show()
            //showProgress(false)
            Log.d("DOWNLOADJOB", "ClickListener")

            videoLooke.start()
            playMusic()
        }

        videoLooke.setOnPreparedListener(MediaPlayer.OnPreparedListener {video ->
            Toast.makeText(this, "Entrou no OnPreparedListener", Toast.LENGTH_SHORT).show()
            showProgress(false)
            Log.d("DOWNLOADJOB", "OnPreparedListener")
            Log.d("DOWNLOADJOB", "show FALSE")

        })
        videoLooke.setOnCompletionListener {
            videoLooke.start()
        }

        mediaPlayer.setOnCompletionListener {
            Log.d("DOWNLOADJOB", "OnCompletionListener")
            videoLooke.stopPlayback()
            txtLoadingMessage.text = "F.I.M"
        }


        txtLookeName.text = looke?.name
}



        private fun showProgress(show: Boolean) {
            txtLoadingMessage.visibility = if (show) View.VISIBLE else View.GONE
            pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun playMusic(){
        Log.d("DOWNLOADJOB", "playMusic")
        mediaPlayer.prepare()
        mediaPlayer.start()

    }


    suspend fun playVideo(controller: MediaController){
        Log.d("DOWNLOADJOB", "playVideo")
        //videoLooke.setMediaController(controller)
        //controller.setMediaPlayer(videoLooke)
        //controller.setAnchorView(videoLooke)
    }

    private fun carregarTarefas(sg: String,controller: MediaController){
       downloadJob = launch{
            coroutineScope{
                showProgress(true)
                Log.d("DOWNLOADJOB - LAUNCH", "show TRUE")

                withContext(Dispatchers.Default){playVideo(controller)}
            }

       }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}

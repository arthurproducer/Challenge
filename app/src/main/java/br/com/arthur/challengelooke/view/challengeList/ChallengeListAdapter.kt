package br.com.arthur.challengelooke.view.challengeList

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.challengelooke.R
import br.com.arthur.challengelooke.model.Looke
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_looke.view.*

class ChallengeListAdapter(
    internal val lookes: List<Looke>,
    private val picasso : Picasso,
    private val callback: (Looke) -> Unit):
    RecyclerView.Adapter<ChallengeListAdapter.VH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListAdapter.VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_looke,parent,false)
        val vh = VH(v)
        return vh
    }

    override fun getItemCount(): Int = lookes.size

    override fun onBindViewHolder(holder: ChallengeListAdapter .VH, position: Int) {
        val looke = lookes[position]
        holder.bindView(looke,picasso,callback)

    }

    class VH(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(
            looke: Looke,
            picasso: Picasso,
            clickListener: (Looke) -> Unit
        ) = with(itemView) {
            txtName.text = looke.name
            imgMusic.setOnClickListener {
                val url = looke.sg
                val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(url)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
                txtVideo.text = looke.bg

            }
            picasso.load(looke.im).into(imgCover)

            setOnClickListener { clickListener(looke) }


        }
    }
}

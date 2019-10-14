package br.com.arthur.challengelooke.view.challengeList


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.arthur.challengelooke.R
import br.com.arthur.challengelooke.common.LookeHttp
import br.com.arthur.challengelooke.model.Looke
import br.com.arthur.challengelooke.view.challengeDetails.ChallengeDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_challenge_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.parceler.Parcels

class ChallengeListFragment : Fragment() {

    val challengelistViewModel: ChallengeListViewModel by viewModel()

    private var asyncTask: BooksDownloadTask? = null

    private val lookeList = mutableListOf<Looke>()

    private var adapter = ChallengeListAdapter(lookeList, Picasso.get(),this::onDetailsItemClick)

    //val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvlistLooke.adapter = adapter

        val layoutManager = LinearLayoutManager(context)

        rvlistLooke.layoutManager = layoutManager

        if (lookeList.isNotEmpty()) {
            showProgress(false)
        } else {
            if(asyncTask == null){
                if(LookeHttp.hasConnection(requireContext())){
                    startDownloadJson()
                } else {
                    pbLoading.visibility = View.GONE
                    txtLoadingMessage.setText(R.string.error_no_connection)
                }
            } else if (asyncTask?.status == AsyncTask.Status.RUNNING){
                showProgress(true)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            txtLoadingMessage.setText(R.string.message_progress)
        }
        txtLoadingMessage.visibility = if (show) View.VISIBLE else View.GONE
        pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun startDownloadJson(){
        if(asyncTask?.status != AsyncTask.Status.RUNNING){
            asyncTask = BooksDownloadTask()
            asyncTask?.execute()
        }
    }


    private fun updateLookeList(result: List<Looke>?) {
        if (result != null) {
            lookeList.clear()
            lookeList.addAll(result)
        } else {
            txtLoadingMessage.setText(R.string.error_load_looke_data)
        }
        adapter?.notifyDataSetChanged()
        asyncTask = null
    }


    private fun onDetailsItemClick(looke : Looke){
        val s = "${looke.name}\n Foi clicado"
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, ChallengeDetailsActivity::class.java)
        intent.putExtra("looke", Parcels.wrap(looke))
        startActivity(intent)
    }



    inner class BooksDownloadTask : AsyncTask<Void, Void, List<Looke>?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            showProgress(true)
        }

        override fun doInBackground(vararg p0: Void?): List<Looke>? {
            return LookeHttp.loadLookeGson()
        }

        override fun onPostExecute(data: List<Looke>?) {
            super.onPostExecute(data)
            showProgress(false)
            updateLookeList(data)
        }
    }
}

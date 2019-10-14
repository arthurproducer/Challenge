package br.com.arthur.challengelooke.view.challengeDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.arthur.challengelooke.R
import br.com.arthur.challengelooke.view.challengeList.ChallengeListFragment

class ChallengeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_details)

        if(savedInstanceState == null){
            showChallengeDetailsFragment()
        }
}


    private fun showChallengeDetailsFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val challengeDetailsFragment = ChallengeDetailsFragment()
        fragmentTransaction.add(R.id.challengeDetailsFragment, challengeDetailsFragment)
        fragmentTransaction.commit()    }

}

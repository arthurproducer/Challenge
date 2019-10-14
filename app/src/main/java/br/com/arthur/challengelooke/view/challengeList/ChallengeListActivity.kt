package br.com.arthur.challengelooke.view.challengeList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.arthur.challengelooke.R

class ChallengeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_list)
        if (savedInstanceState == null) {
            showChallengeListFragment()
        }
    }

    private fun showChallengeListFragment() {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val challengeListFragment = ChallengeListFragment()
        fragmentTransaction.add(R.id.challengeListFragment, challengeListFragment)
        fragmentTransaction.commit()
    }
}
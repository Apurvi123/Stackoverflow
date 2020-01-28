package com.app.stackoverflow.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.stackoverflow.R
import com.app.stackoverflow.repository.SORepository
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var soRepository: SORepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        CoroutineScope(Dispatchers.Main).launch {
            soRepository.fetchQuestions { Log.d(TAG, it.message!!) }
        }

        allQuestions.setOnClickListener {
            val intent = Intent(applicationContext, QuestionsActivity::class.java)
            startActivity(intent)
        }

        guessedQuestions.setOnClickListener {
            val intent = Intent(applicationContext, GuessedQuestionsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            soRepository.fetchGuessedQuestions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        soRepository.clearAll()
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}



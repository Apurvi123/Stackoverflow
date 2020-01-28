package com.app.stackoverflow.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stackoverflow.R
import com.app.stackoverflow.repository.SORepository
import com.app.stackoverflow.ui.adapter.AnswersAdapter
import com.app.stackoverflow.ui.util.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.coroutines.*
import javax.inject.Inject

class AnswersActivity : AppCompatActivity() {

    @Inject
    lateinit var soRepository: SORepository
    private var answersAdapter: AnswersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)

        AndroidInjection.inject(this)

        val recyclerView = answerList
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)

        val intent = intent
        val questionId = intent.getLongExtra(Constants.INTENT_KEY_QUESTION_ID, 0)

        runBlocking {
            soRepository.fetchAnswersForQuestion(questionId) { Log.d(TAG, it.message!!) }
            delay(500L)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val answerList = soRepository.getAnswersForQuestion(questionId)
            if (!answerList.isNullOrEmpty()) {
                val acceptedList = mutableListOf<Long>()
                answerList.forEach {
                    if (it.isAccepted) acceptedList.add(it.answerId)
                }

                if (acceptedList.size >= 1) {
                    answersAdapter = AnswersAdapter(answerList, acceptedList, soRepository)
                    recyclerView?.adapter = answersAdapter
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No accepted answers for this question!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "No answers for this question!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object{
        private const val TAG = "AnswersActivity"
    }
}



package com.app.stackoverflow.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.stackoverflow.R
import com.app.stackoverflow.repository.SORepository
import com.app.stackoverflow.ui.adapter.QuestionsAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_guessed_questions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GuessedQuestionsActivity : AppCompatActivity() {

    @Inject
    lateinit var soRepository: SORepository
    private var questionsAdapter: QuestionsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessed_questions)

        AndroidInjection.inject(this)

        guessedList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        guessedList?.layoutManager = LinearLayoutManager(applicationContext)

        val questions = soRepository.getGuessedQuestions()

        if (!questions.isNullOrEmpty()) {
            questionsAdapter = QuestionsAdapter(questions)
            guessedList?.adapter = questionsAdapter
        } else {
            Toast.makeText(applicationContext, "No guessed questions!!", Toast.LENGTH_LONG).show()
        }
    }
}

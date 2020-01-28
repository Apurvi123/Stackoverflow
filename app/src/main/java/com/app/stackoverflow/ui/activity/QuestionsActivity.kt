package com.app.stackoverflow.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.stackoverflow.repository.SORepository
import com.app.stackoverflow.ui.adapter.QuestionsAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.app.stackoverflow.R
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {

    @Inject
    lateinit var soRepository: SORepository
    private var recyclerView: RecyclerView? = null
    private var recyclerViewState: Parcelable? = null
    private var questionsAdapter: QuestionsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        AndroidInjection.inject(this)

        recyclerView = questionList
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                questionsAdapter?.filter?.filter(newText)
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()

        questionsAdapter = QuestionsAdapter(soRepository.getAllQuestions())
        recyclerView?.adapter = questionsAdapter

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the scroll state of recyclerview
        recyclerViewState = recyclerView?.layoutManager?.onSaveInstanceState();
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // restore the scroll state of recyclerview
        recyclerView?.layoutManager?.onRestoreInstanceState(recyclerViewState);
    }

}


package com.app.stackoverflow.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.stackoverflow.R
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity
import com.app.stackoverflow.ui.activity.AnswersActivity
import com.app.stackoverflow.ui.activity.WebActivity
import com.app.stackoverflow.ui.util.Constants

class QuestionsAdapter (
    private val questionList: List<QuestionEntity>
): RecyclerView.Adapter<QuestionsAdapter.QuestionHolder>(), Filterable {

    private var filteredData = questionList.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuestionHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.questions_adapter_layout, parent,
                false), parent.context
        )

    override fun getItemCount(): Int = if (filteredData.isNullOrEmpty()) 0 else filteredData.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        holder.bind(filteredData[position])
    }

    override fun getFilter(): Filter = filter

    private val filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            synchronized(this) {
                return FilterResults().apply {
                    if (constraint != null && constraint.isNotEmpty()) {
                        val filterString = constraint.toString().toLowerCase()
                        val filtered = questionList
                            .filter { it.toString().toLowerCase().contains(filterString) }

                        count = filtered.size
                        values = filtered
                    } else {
                        values = questionList
                        count = questionList.size
                    }
                }
            }
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            @Suppress("UNCHECKED_CAST")
            filteredData = results.values as List<QuestionEntity>
            notifyDataSetChanged()
        }
    }

    class QuestionHolder(itemView: View, private val appContext: Context) : RecyclerView.ViewHolder(itemView) {
        private val questionTitle = itemView.findViewById<TextView>(R.id.title)
        private val userName = itemView.findViewById<TextView>(R.id.userName)
        private val webLink = itemView.findViewById<TextView>(R.id.webLink)

        fun bind(question: QuestionEntity) {
            questionTitle.text = question.questionTitle
            webLink.text = question.webLink
            userName.text = String.format("%s: %s", "Posted by", question.displayName)

            webLink.movementMethod = LinkMovementMethod.getInstance()
            webLink.setOnClickListener {
                val intent = Intent(appContext, WebActivity::class.java)
                intent.putExtra(Constants.INTENT_KEY_URL, question.webLink)
                appContext.startActivity(intent)
            }

            itemView.findViewById<LinearLayout>(R.id.rowLayout).setOnClickListener {
                val intent = Intent(appContext, AnswersActivity::class.java)
                intent.putExtra(Constants.INTENT_KEY_QUESTION_ID, question.questionId)
                appContext.startActivity(intent)
            }
        }
    }

}
package com.app.stackoverflow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.app.stackoverflow.R
import com.app.stackoverflow.repository.SORepository
import com.app.stackoverflow.repository.localsource.entity.AnswerEntity

class AnswersAdapter (
    private val answerList: List<AnswerEntity>,
    private val acceptedList: List<Long>,
    private val soRepository: SORepository
): RecyclerView.Adapter<AnswersAdapter.AnswerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnswerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.answers_adapter_layout, parent,
                false), parent.context
        )

    override fun getItemCount(): Int = answerList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.bind(answerList[position], acceptedList, soRepository)
    }

    class AnswerHolder(itemView: View, private val appContext: Context) : RecyclerView.ViewHolder(itemView) {
        private val answerId = itemView.findViewById<TextView>(R.id.answerId)
        private val userName = itemView.findViewById<TextView>(R.id.userName)
        private val answerLayout = itemView.findViewById<RelativeLayout>(R.id.answerLayout)

        fun bind(answer: AnswerEntity, acceptedList: List<Long>, soRepository: SORepository) {
            answerId.text = answer.answerId.toString()
            userName.text = String.format("%s: %s", "Answered by", answer.displayName)

            answerLayout.setOnClickListener {
                soRepository.updateGuessedQuestion(answer.questionId, true)
                if (acceptedList.contains(answer.answerId)) {
                    Toast.makeText(appContext, "Guessed the right answer", Toast.LENGTH_SHORT).show()
                } else {
                    val message = String.format("Sorry! Correct Answer is %s", acceptedList.toString())
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
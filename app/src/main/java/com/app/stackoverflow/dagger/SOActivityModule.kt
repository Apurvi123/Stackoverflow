package com.app.stackoverflow.dagger

import com.app.stackoverflow.ui.activity.AnswersActivity
import com.app.stackoverflow.ui.activity.GuessedQuestionsActivity
import com.app.stackoverflow.ui.activity.MainActivity
import com.app.stackoverflow.ui.activity.QuestionsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SOActivityModule {

    @ContributesAndroidInjector()
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun questionsActivity(): QuestionsActivity

    @ContributesAndroidInjector()
    abstract fun answersActivity(): AnswersActivity

    @ContributesAndroidInjector()
    abstract fun guessedQuestionsActivity(): GuessedQuestionsActivity
}
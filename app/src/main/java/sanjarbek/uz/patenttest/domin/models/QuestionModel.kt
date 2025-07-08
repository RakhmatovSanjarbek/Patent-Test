package sanjarbek.uz.patenttest.domin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class QuestionModel(
    val questionId: Int? = null,
    val questionType: QuestionType? = null,
    val questionTitle: String? = null,
    val question: String? = null,
    val questionMedia: Int? = null,
    val questionDescription: String? = null,
    val answersString: List<String>? = null,
    val answersImages: List<Int>? = null,
    val inputQuestions: List<String>? = null,
    val inputAnswers: List<String>? = null,
    val answerInput: String? = null,
    val enterInputString: String? = null,
    val isAnswerInputCorrect: Boolean? = null,
    val correctAnswerPosition: Int? = null,
    val selectedAnswerPosition: Int? = null,
    val isAnswerMarked: Boolean = false,
): Parcelable

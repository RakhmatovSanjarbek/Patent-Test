package sanjarbek.uz.patenttest.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import sanjarbek.uz.patenttest.R
import sanjarbek.uz.patenttest.databinding.FragmentCorrectAnswersBinding
import sanjarbek.uz.patenttest.domin.models.QuestionModel
import sanjarbek.uz.patenttest.domin.models.QuestionType
import sanjarbek.uz.patenttest.domin.models.VariantModel
import sanjarbek.uz.patenttest.utils.Constants

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class CorrectAnswersFragment : Fragment() {

    private var _binding: FragmentCorrectAnswersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CorrectAnswerControlAdapter

    private var currentQuestionIndex = 0
    private var questionList: MutableList<QuestionModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCorrectAnswersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CorrectAnswerControlAdapter {
            currentQuestionIndex = it
            showCurrentQuestion()
        }
        binding.rvQuestionControl.adapter = adapter

        arguments?.let {
            val variantList = it.getParcelable<VariantModel>(Constants.String.RESULT_LIST)
            binding.tvVariantNumber.text=variantList?.variantNumber?:" "
            variantList?.questionModel?.let { it1 -> setQuestions(it1) }
        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun setQuestions(questions: List<QuestionModel>) {
        questionList = questions.sortedBy { it.questionId }.toMutableList()
        currentQuestionIndex = 0
        adapter.submitList(questionList)
        adapter.updateSelectedPosition(0)
        showCurrentQuestion()
    }

    private fun showCurrentQuestion() {
        if (currentQuestionIndex in questionList.indices) {
            val question = questionList[currentQuestionIndex]
            binding.tvQuestionNumber.text = "${question.questionId} / ${questionList.size}"

            when (question.questionType) {
                QuestionType.TEXT -> questionTypeText(question)
                QuestionType.AUDIO -> questionTypeAudio(question)
                QuestionType.INPUT -> questionTypeInput(question)
                QuestionType.IMAGE -> questionTypeImage(question)
                null -> {}
            }
        }
    }

    private fun questionTypeInput(questionModel: QuestionModel) {
        with(binding) {
            tvQuestionTitle.visibility = View.VISIBLE
            tvQuestion.visibility = View.VISIBLE
            tvQuestionDescription.visibility = View.VISIBLE
            audioPlayerView.visibility = View.GONE
            llInputAnswer.visibility = View.VISIBLE
            cvAnswerA.visibility = View.GONE
            cvAnswerB.visibility = View.GONE
            cvAnswerC.visibility = View.GONE
            imgAnswerA.visibility = View.GONE
            imgAnswerB.visibility = View.GONE
            imgAnswerC.visibility = View.GONE
            tvCorrectAnswer.visibility = View.GONE

            tvQuestionTitle.text = questionModel.questionTitle ?: ""
            tvQuestion.text = questionModel.question ?: ""
            tvQuestionDescription.text = questionModel.questionDescription ?: ""

            tvInputQuestionA.text = questionModel.inputQuestions?.getOrNull(0) ?: ""
            tvInputQuestionB.text = questionModel.inputQuestions?.getOrNull(1) ?: ""
            tvInputQuestionC.text = questionModel.inputQuestions?.getOrNull(2) ?: ""

            tvInputAnswerA.text = questionModel.inputAnswers?.getOrNull(0) ?: ""
            tvInputAnswerB.text = questionModel.inputAnswers?.getOrNull(1) ?: ""
            tvInputAnswerC.text = questionModel.inputAnswers?.getOrNull(2) ?: ""

            if (questionModel.enterInputString.equals(questionModel.answerInput)) {
                etInputAnswer.setText(questionModel.enterInputString)
                etInputAnswer.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_green
                    )
                )
            } else {
                etInputAnswer.setText(questionModel.enterInputString)
                etInputAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                tvCorrectAnswer.visibility = View.VISIBLE
                tvCorrectAnswer.text = questionModel.answerInput
            }
        }
    }

    private fun questionTypeText(questionModel: QuestionModel) {
        with(binding) {
            tvAnswerA.visibility = View.VISIBLE
            tvAnswerB.visibility = View.VISIBLE
            tvAnswerC.visibility = View.VISIBLE
            tvQuestionDescription.visibility = View.VISIBLE
            tvQuestionTitle.visibility = View.VISIBLE
            tvQuestion.visibility = View.VISIBLE
            audioPlayerView.visibility = View.GONE
            llInputAnswer.visibility = View.GONE
            imgAnswerA.visibility = View.GONE
            imgAnswerB.visibility = View.GONE
            imgAnswerC.visibility = View.GONE

            val answers = questionModel.answersString

            if (answers != null) {
                when (answers.size) {
                    2 -> {
                        cvAnswerA.visibility = View.VISIBLE
                        cvAnswerB.visibility = View.VISIBLE
                        cvAnswerC.visibility = View.GONE
                    }

                    3 -> {
                        cvAnswerA.visibility = View.VISIBLE
                        cvAnswerB.visibility = View.VISIBLE
                        cvAnswerC.visibility = View.VISIBLE
                    }

                    else -> {
                        cvAnswerA.visibility = View.GONE
                        cvAnswerB.visibility = View.GONE
                        cvAnswerC.visibility = View.GONE
                    }
                }

                tvAnswerA.text = answers.getOrNull(0) ?: ""
                tvAnswerB.text = answers.getOrNull(1) ?: ""
                tvAnswerC.text = answers.getOrNull(2) ?: ""

            }

            tvQuestionTitle.text = questionModel.questionTitle ?: ""
            tvQuestion.text = questionModel.question ?: ""
            tvQuestionDescription.text = questionModel.questionDescription ?: ""

            updateAnswerCardColors(questionModel)
        }
    }

    private fun questionTypeAudio(questionModel: QuestionModel) {
        with(binding) {
            audioPlayerView.visibility = View.VISIBLE
            tvQuestionTitle.visibility = View.VISIBLE
            tvQuestion.visibility = View.GONE
            tvQuestionDescription.visibility = View.VISIBLE
            tvAnswerA.visibility = View.VISIBLE
            tvAnswerB.visibility = View.VISIBLE
            tvAnswerC.visibility = View.VISIBLE
            cvAnswerA.visibility = View.VISIBLE
            cvAnswerB.visibility = View.VISIBLE
            cvAnswerC.visibility = View.VISIBLE
            llInputAnswer.visibility = View.GONE
            imgAnswerA.visibility = View.GONE
            imgAnswerB.visibility = View.GONE
            imgAnswerC.visibility = View.GONE

            questionModel.questionMedia?.let { audioPlayerView.setAudioId(it) }
            tvQuestionTitle.text = questionModel.questionTitle ?: ""
            tvQuestionDescription.text = questionModel.questionDescription ?: ""
            tvAnswerA.text = questionModel.answersString?.getOrNull(0) ?: ""
            tvAnswerB.text = questionModel.answersString?.getOrNull(1) ?: ""
            tvAnswerC.text = questionModel.answersString?.getOrNull(2) ?: ""

            updateAnswerCardColors(questionModel)
        }
    }

    private fun questionTypeImage(questionModel: QuestionModel) {
        with(binding) {
            cvAnswerB.visibility = View.VISIBLE
            cvAnswerA.visibility = View.VISIBLE
            cvAnswerC.visibility = View.VISIBLE
            tvQuestion.visibility = View.VISIBLE
            imgAnswerA.visibility = View.VISIBLE
            imgAnswerB.visibility = View.VISIBLE
            imgAnswerC.visibility = View.VISIBLE

            tvQuestionDescription.visibility = View.GONE
            tvQuestionTitle.visibility = View.GONE
            audioPlayerView.visibility = View.GONE
            llInputAnswer.visibility = View.GONE
            tvAnswerA.visibility = View.GONE
            tvAnswerB.visibility = View.GONE
            tvAnswerC.visibility = View.GONE

            tvQuestionTitle.text = questionModel.questionTitle ?: ""
            tvQuestion.text = questionModel.question ?: ""
            tvQuestionDescription.text = questionModel.questionDescription ?: ""
            questionModel.answersImages?.getOrNull(0)?.let { imgAnswerA.setImageResource(it) }
            questionModel.answersImages?.getOrNull(1)?.let { imgAnswerB.setImageResource(it) }
            questionModel.answersImages?.getOrNull(2)?.let { imgAnswerC.setImageResource(it) }

            updateAnswerCardColors(questionModel)
        }
    }


    private fun updateAnswerCardColors(
        questionModel: QuestionModel,
    ) {
            val cards = listOf(binding.cvAnswerA, binding.cvAnswerB, binding.cvAnswerC)
            val texts = listOf(binding.tvAnswerA, binding.tvAnswerB, binding.tvAnswerC)

        val green = ContextCompat.getColor(requireContext(), R.color.dark_green)
        val red = ContextCompat.getColor(requireContext(), R.color.dark_red)
        val white = ContextCompat.getColor(requireContext(), R.color.white)
        val gray = ContextCompat.getColor(requireContext(), R.color.light_gray)

        for (i in cards.indices) {
            cards[i].setCardBackgroundColor(gray)
            texts[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        if (questionModel.correctAnswerPosition == questionModel.selectedAnswerPosition && questionModel.correctAnswerPosition != null) {
            cards[questionModel.correctAnswerPosition].setCardBackgroundColor(green)
            texts[questionModel.correctAnswerPosition].setTextColor(white)
        } else {
            questionModel.correctAnswerPosition?.let {
                cards[it].setCardBackgroundColor(green)
                texts[it].setTextColor(white)
            }
            questionModel.selectedAnswerPosition?.let {
                cards[it].setCardBackgroundColor(red)
                texts[it].setTextColor(white)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package sanjarbek.uz.patenttest.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import sanjarbek.uz.patenttest.R
import sanjarbek.uz.patenttest.databinding.FragmentWorkTestBinding
import sanjarbek.uz.patenttest.domin.models.QuestionModel
import sanjarbek.uz.patenttest.domin.models.QuestionType
import sanjarbek.uz.patenttest.domin.models.VariantModel
import sanjarbek.uz.patenttest.utils.Constants

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class WorkTestFragment : Fragment() {

    private lateinit var backCallback: OnBackPressedCallback

    private var currentQuestionIndex = 0
    private var questionList: MutableList<QuestionModel> = mutableListOf()

    private var variantNumber: String? = null

    private var _binding: FragmentWorkTestBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: QuestionControlAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showBackDialog()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        adapter = QuestionControlAdapter {
            currentQuestionIndex = it
            showCurrentQuestion()
        }

        binding.rvQuestionControl.adapter = adapter

        arguments?.let {
            val variantList = it.getParcelable<VariantModel>(Constants.String.VARIANT_LIST)
            binding.tvVariantNumber.text=variantList?.variantNumber?:" "
            variantNumber = variantList?.variantNumber
            variantList?.questionModel?.let { it1 -> setQuestions(it1) }
        }

        binding.ivBack.setOnClickListener {
            showBackDialog()
        }

        binding.btnNext.setOnClickListener {
            val current = questionList[currentQuestionIndex]
            if (current.questionType == QuestionType.INPUT) {
                val input = binding.etInputAnswer.text.toString().trim()
                questionList[currentQuestionIndex] = current.copy(
                    enterInputString = input,
                    isAnswerMarked = input.length > 3
                )
            }
            if (currentQuestionIndex < questionList.size - 1) {
                currentQuestionIndex++
                adapter.updateSelectedPosition(currentQuestionIndex)
                showCurrentQuestion()
            }
        }

        binding.btnFinish.setOnClickListener {
            showResultDialog()
        }
    }

    private fun setQuestions(questions: List<QuestionModel>) {
        questionList = questions.sortedBy { it.questionId }.toMutableList()
        currentQuestionIndex = 0
        adapter.submitList(questionList)
        adapter.updateSelectedPosition(0)
        showCurrentQuestion()
        updateButtonStates()
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
            isSelectedCard(question)
            updateButtonStates()
        }
    }

    private fun updateButtonStates() {
        val current = questionList[currentQuestionIndex]
        binding.btnNext.isEnabled = when (current.questionType) {
            QuestionType.INPUT -> (binding.etInputAnswer.text.toString().length > 3)
            else -> current.isAnswerMarked
        }
        binding.btnFinish.isEnabled = questionList.all {
            if (it.questionType == QuestionType.INPUT) {
                (((it.enterInputString?.length ?: 4) > 3))
            } else it.isAnswerMarked
        }
    }

    private fun selectCardAnswer(selectedCard: MaterialCardView, selectedTextView: TextView) {
        with(binding) {
            val cards = listOf(cvAnswerA, cvAnswerB, cvAnswerC)
            val texts = listOf(tvAnswerA, tvAnswerB, tvAnswerC)

            val selectedIndex = cards.indexOf(selectedCard)

            for (i in cards.indices) {
                if (i == selectedIndex) {
                    cards[i].setCardBackgroundColor(
                        ContextCompat.getColor(
                            selectedCard.context,
                            R.color.color_blue
                        )
                    )
                    texts[i].setTextColor(
                        ContextCompat.getColor(
                            selectedTextView.context,
                            R.color.white
                        )
                    )
                } else {
                    cards[i].setCardBackgroundColor(
                        ContextCompat.getColor(
                            selectedCard.context,
                            R.color.light_gray
                        )
                    )
                    texts[i].setTextColor(
                        ContextCompat.getColor(
                            selectedTextView.context,
                            R.color.black
                        )
                    )
                }
            }

            val updatedQuestion = questionList[currentQuestionIndex].copy(
                selectedAnswerPosition = selectedIndex,
                isAnswerMarked = true
            )
            questionList[currentQuestionIndex] = updatedQuestion

            updateButtonStates()
        }
    }

    private fun isSelectedCard(questionModel: QuestionModel) {
        with(binding) {
            val cards = listOf(cvAnswerA, cvAnswerB, cvAnswerC)
            val texts = listOf(tvAnswerA, tvAnswerB, tvAnswerC)

            for (i in cards.indices) {
                val isSelected = i == questionModel.selectedAnswerPosition
                cards[i].setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (isSelected && questionModel.isAnswerMarked) R.color.color_blue else R.color.light_gray
                    )
                )
                texts[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (isSelected && questionModel.isAnswerMarked) R.color.white else R.color.black
                    )
                )
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

            etInputAnswer.setText(questionModel.enterInputString ?: "")

            etInputAnswer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString().trim()
                    val wordCount = input.length

                    questionList[currentQuestionIndex] =
                        questionList[currentQuestionIndex].copy(
                            enterInputString = input,
                            isAnswerMarked = wordCount > 3
                        )
                    updateButtonStates()
                }
            })

            tvQuestionTitle.text = questionModel.questionTitle ?: ""
            tvQuestion.text = questionModel.question ?: ""
            tvQuestionDescription.text = questionModel.questionDescription ?: ""

            tvInputQuestionA.text = questionModel.inputQuestions?.getOrNull(0) ?: ""
            tvInputQuestionB.text = questionModel.inputQuestions?.getOrNull(1) ?: ""
            tvInputQuestionC.text = questionModel.inputQuestions?.getOrNull(2) ?: ""

            tvInputAnswerA.text = questionModel.inputAnswers?.getOrNull(0) ?: ""
            tvInputAnswerB.text = questionModel.inputAnswers?.getOrNull(1) ?: ""
            tvInputAnswerC.text = questionModel.inputAnswers?.getOrNull(2) ?: ""
        }
    }

    private fun showResultDialog() {
        val correctCount = questionList.count {
            when (it.questionType) {
                QuestionType.INPUT -> {
                    it.enterInputString != null &&
                            it.answerInput != null &&
                            it.answerInput.equals(it.enterInputString, ignoreCase = true)
                }
                else -> {
                    it.correctAnswerPosition != null &&
                            it.selectedAnswerPosition == it.correctAnswerPosition
                }
            }
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Natija")
            .setMessage("To'g'ri javoblar soni: $correctCount / ${questionList.size} \nTest natijalari oynasiga otish")
            .setPositiveButton("OK") { dialog, _ ->
                val resultList = VariantModel(
                    variantNumber = variantNumber,
                    questionModel = questionList
                )
                val bundle = Bundle().apply {
                    putParcelable(Constants.String.RESULT_LIST, resultList)
                }
                    val fragment = CorrectAnswersFragment()
                    fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
                dialog.dismiss()
            }
            .show()
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

            cvAnswerA.setOnClickListener {
                selectCardAnswer(cvAnswerA, tvAnswerA)
            }
            cvAnswerB.setOnClickListener {
                selectCardAnswer(cvAnswerB, tvAnswerB)
            }
            cvAnswerC.setOnClickListener {
                selectCardAnswer(cvAnswerC, tvAnswerC)
            }
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
            cvAnswerA.setOnClickListener {
                selectCardAnswer(cvAnswerA, tvAnswerA)
            }
            cvAnswerB.setOnClickListener {
                selectCardAnswer(cvAnswerB, tvAnswerB)
            }
            cvAnswerC.setOnClickListener {
                selectCardAnswer(cvAnswerC, tvAnswerC)
            }
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
            cvAnswerA.setOnClickListener {
                selectCardAnswer(cvAnswerA, tvAnswerA)
            }
            cvAnswerB.setOnClickListener {
                selectCardAnswer(cvAnswerB, tvAnswerB)
            }
            cvAnswerC.setOnClickListener {
                selectCardAnswer(cvAnswerC, tvAnswerC)
            }
        }
    }

    private fun showBackDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("Testni yakunlamasdan chiqmoqchimisiz")
            .setPositiveButton("Ha") { _,_->
                backCallback.isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("Yo'q"){dialog, _ -> dialog.dismiss()}
            .show()
            .setCancelable(false)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

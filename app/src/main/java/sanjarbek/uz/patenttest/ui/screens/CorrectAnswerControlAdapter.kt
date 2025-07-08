package sanjarbek.uz.patenttest.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sanjarbek.uz.patenttest.R
import sanjarbek.uz.patenttest.databinding.ItemCepBinding
import sanjarbek.uz.patenttest.domin.models.QuestionModel

class CorrectAnswerControlAdapter(
    private val onItemClick: (Int) -> Unit,
) :
    ListAdapter<QuestionModel, CorrectAnswerControlAdapter.QuestionViewHolder>(DiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION

    fun updateSelectedPosition(newPosition: Int) {
        val previousPosition = selectedPosition
        selectedPosition = newPosition
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }


    inner class QuestionViewHolder(
        private val binding: ItemCepBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: QuestionModel, isSelected: Boolean) = with(binding) {
            tvNumber.text = model.questionId?.toString() ?: "-"

            if (model.enterInputString!=null && model.answerInput!=null ) {
                if (model.enterInputString == model.answerInput) {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.dark_green
                        )
                    )
                    tvNumber.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                }else{
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.dark_red
                        )
                    )
                    tvNumber.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                }
            }
            else if(model.enterInputString==null && model.answerInput==null ){
                if (model.correctAnswerPosition==model.selectedAnswerPosition) {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.dark_green
                        )
                    )
                    tvNumber.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                }else{
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.dark_red
                        )
                    )
                    tvNumber.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                }
            }
            if (isSelected) {
                cardView.strokeWidth = 4
                cardView.strokeColor = ContextCompat.getColor(root.context, R.color.color_blue)
            } else {
                cardView.strokeWidth = 0
            }

            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
                onItemClick(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemCepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model, position == selectedPosition)
    }

    class DiffCallback : DiffUtil.ItemCallback<QuestionModel>() {
        override fun areItemsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem.questionId == newItem.questionId
        }

        override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem == newItem
        }
    }
}
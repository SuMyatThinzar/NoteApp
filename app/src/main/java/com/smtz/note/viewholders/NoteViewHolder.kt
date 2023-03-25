package com.smtz.note.viewholders

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smtz.note.R
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ViewItemNoteBinding
import com.smtz.note.delegates.NoteDelegate
import com.smtz.note.utils.MONTHS_ARRAY
import com.smtz.note.utils.SELECTED_ITEM_SCALE
import com.smtz.note.utils.mNoteList

class NoteViewHolder(private val binding: ViewItemNoteBinding, private val mDelegate: NoteDelegate) : RecyclerView.ViewHolder(binding.root) {

    private var mDataVO: NoteVO? = null

    init {
        binding.root.setOnClickListener {
            // additional setting အတွက် select လုပ်ထားတဲ့ list
            var selectedNoteCount = 0

            mNoteList.forEach {
                if (it.checked == true) {
                    selectedNoteCount++
                }
            }
            // LongClick to SingleClick
            when (selectedNoteCount) {
                0 -> mDataVO?.let { mDelegate.onTapNote(it.id) }
                else -> setListener()
            }
        }

        binding.root.setOnLongClickListener {
            setListener()
            true
        }
    }

    private fun setListener() {
        mDataVO?.let {
            mDelegate.onLongClickNote(it.id)
            if (binding.ivChecked.visibility == View.INVISIBLE) {
                smallerView(true)
            } else {
                binding.ivChecked.visibility = View.INVISIBLE
                smallerView(false)
            }
        }
    }

    fun bindData(data: NoteVO, position: Int) {
        mDataVO = data

        binding.tvTitle.text = data.title
        binding.tvContent.text = data.content
        binding.tvDate.text = data.date

        when (position % 6) {
            0 -> changeColor(R.color.pickBlue, true)
            1 -> changeColor(R.color.pickGreen, false)
            2 -> changeColor(R.color.pickLightBlue, false)
            3 -> changeColor(R.color.pickPeach, false)
            4 -> changeColor(R.color.pickYellow, false)
            5 -> changeColor(R.color.pickPurple, true)
        }

        smallerView(false)
    }

    private fun changeColor(color: Int, txtWhite: Boolean) {
        binding.root.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, color)

        if (txtWhite) {
            binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            binding.tvContent.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        } else {
            binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context,R.color.black))
            binding.tvContent.setTextColor(ContextCompat.getColor(binding.root.context,R.color.black))
            binding.line.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.background_line_black))
        }
    }

    private fun smallerView(changeView: Boolean) {
        if (changeView) {
            binding.ivChecked.visibility = View.VISIBLE

            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                itemView,
                PropertyValuesHolder.ofFloat(View.SCALE_X, SELECTED_ITEM_SCALE),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, SELECTED_ITEM_SCALE)
            )
            scaleDown.duration = 200

            scaleDown.start()

            // Play the animations together
//            val animatorSet = AnimatorSet()
//            animatorSet.playTogether(scaleDown)
//            animatorSet.start()

        } else {
            binding.ivChecked.visibility = View.INVISIBLE

            val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                itemView,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
            )
            scaleUp.duration = 200

            scaleUp.start()
        }
    }

    //
//    private fun setMargins(additionalMargin: Int) {
//        val params = binding.root.layoutParams as RecyclerView.LayoutParams
//
//        params.setMargins(
//            params.leftMargin + additionalMargin,
//            params.topMargin + additionalMargin,
//            params.rightMargin + additionalMargin,
//            params.bottomMargin + additionalMargin
//        )
//
//        binding.root.layoutParams = params
//    }

}

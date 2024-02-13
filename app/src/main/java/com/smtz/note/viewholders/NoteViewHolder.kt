package com.smtz.note.viewholders

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smtz.note.R
import com.smtz.note.common.NoteSingleton
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ViewItemNoteBinding
import com.smtz.note.delegates.NoteDelegate
import com.smtz.note.utils.SELECTED_ITEM_SCALE_LARGE
import com.smtz.note.utils.SELECTED_ITEM_SCALE_SMALL

class NoteViewHolder(private val binding: ViewItemNoteBinding, private val mDelegate: NoteDelegate) : RecyclerView.ViewHolder(binding.root) {

    private var mDataVO: NoteVO? = null

    init {
        binding.root.setOnClickListener {
            if (NoteSingleton.instance.getSelectedNoteList().isEmpty()) {
                mDataVO?.let { mDelegate.onTapNote(it.id) }
            } else {
                setNoteSelection()
            }
        }

        binding.root.setOnLongClickListener {
            setNoteSelection()
            true
        }
    }

    private fun setNoteSelection() {
        mDataVO?.let {
            if (it.id in NoteSingleton.instance.getSelectedNoteList()) {
                binding.ivChecked.visibility = View.INVISIBLE
                NoteSingleton.instance.removeSelectedNote(it.id)  // add the selected note
                smallerView(false)  // animate bigger
            } else {
                binding.ivChecked.visibility = View.VISIBLE
                NoteSingleton.instance.addSelectedNote(it.id)  // add the selected note
                smallerView(true)  // animate smaller
            }
            mDelegate.onLongClickNote()
        }
    }

    fun bindData(data: NoteVO, position: Int) {
        mDataVO = data
        binding.tvTitle.text = data.title
        binding.tvContent.text = data.content
        binding.tvDate.text = data.date

        smallerView(false)

        when (position % 8) {
            0 -> changeColor(R.color.pickBlue, true)
            1 -> changeColor(R.color.pickGreen, false)
            2 -> changeColor(R.color.pickLightBlue, false)
            3 -> changeColor(R.color.pickPeach, false)
            4 -> changeColor(R.color.pick_color_1, false)
            5 -> changeColor(R.color.pick_color_2, true)
            6 -> changeColor(R.color.pick_color_3, true)
            7 -> changeColor(R.color.pick_color_4, true)
        }

        if (data.pinTimeStamp != 0L) {
            binding.ivPin.visibility = View.VISIBLE
        } else {
            binding.ivPin.visibility = View.GONE
        }
    }

    private fun changeColor(color: Int, txtWhite: Boolean) {
        binding.root.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, color)
    }

    private fun smallerView(changeView: Boolean) {
        if (changeView) {
            binding.ivChecked.visibility = View.VISIBLE

            if (mDataVO?.id in NoteSingleton.instance.getSelectedNoteList()) {

                val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    itemView,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, SELECTED_ITEM_SCALE_SMALL),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, SELECTED_ITEM_SCALE_SMALL)
                )
                scaleDown.duration = 150

                scaleDown.start()

            }
            // Play the animations together
//            val animatorSet = AnimatorSet()
//            animatorSet.playTogether(scaleDown)
//            animatorSet.start()

        } else {
            binding.ivChecked.visibility = View.INVISIBLE

            val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                itemView,
                PropertyValuesHolder.ofFloat(View.SCALE_X, SELECTED_ITEM_SCALE_LARGE),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, SELECTED_ITEM_SCALE_LARGE)
            )
            scaleUp.duration = 300

            scaleUp.start()
        }
    }
}

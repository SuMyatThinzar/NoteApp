package com.smtz.note.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ViewItemNoteBinding
import com.smtz.note.delegates.NoteDelegate
import com.smtz.note.viewholders.NoteViewHolder

class NoteAdapter(private val mDelegate: NoteDelegate) : RecyclerView.Adapter<NoteViewHolder>() {

    private var mData: List<NoteVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_item_note,parent,false)
//        return NoteViewHolder(itemView)

        val binding = ViewItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, mDelegate)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    fun setNewData(data: List<NoteVO>){
        mData = data.reversed()
        notifyDataSetChanged()
    }
}
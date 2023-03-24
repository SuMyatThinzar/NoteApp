package com.smtz.note.presenters

import androidx.lifecycle.LifecycleOwner
import com.smtz.note.views.CreateNoteView

interface CreateNotePresenter : BasePresenter<CreateNoteView> {
    fun onUiReady(owner: LifecycleOwner, id: Long)
    fun onTapDone(title: String, content: String, id: Long)
}
package com.smtz.note.views

import com.smtz.note.data.vos.NoteVO

interface CreateNoteView : BaseView {
    fun setUpNoteVO(note: NoteVO?)
}
package com.smtz.note.views

import com.smtz.note.data.vos.NoteVO

interface HomeView : BaseView {
    fun navigateToCreateNoteActivity(id: Long)
    fun showNoteList(noteList: List<NoteVO>)
    fun showAdditionalSetting()
    fun hideAdditionalSetting()
}
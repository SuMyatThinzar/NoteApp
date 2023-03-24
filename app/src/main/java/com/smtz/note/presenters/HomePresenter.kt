package com.smtz.note.presenters

import com.smtz.note.delegates.NoteDelegate
import com.smtz.note.views.HomeView

interface HomePresenter : BasePresenter<HomeView>, NoteDelegate {
    fun onTapCreateNote()
    fun onTapDeleteNote()
    fun clearSelectedNotes()
}
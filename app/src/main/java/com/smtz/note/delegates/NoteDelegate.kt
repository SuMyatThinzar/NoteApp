package com.smtz.note.delegates

interface NoteDelegate {
    fun onTapNote(id: Long)
    fun onLongClickNote()
}
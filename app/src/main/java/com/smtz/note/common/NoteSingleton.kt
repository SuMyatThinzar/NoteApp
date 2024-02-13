package com.smtz.note.common

class NoteSingleton private constructor(){

    private var selectedNoteList: MutableSet<Long> = mutableSetOf()

    companion object {
        val instance: NoteSingleton by lazy {
            Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = NoteSingleton()
    }

    fun cleanup() {
        selectedNoteList = mutableSetOf()
    }

    fun addSelectedNote(noteId: Long) {
        Holder.INSTANCE.selectedNoteList.add(noteId)
    }

    fun removeSelectedNote(noteId: Long) {
        Holder.INSTANCE.selectedNoteList.remove(noteId)
    }

    fun getSelectedNoteList(): MutableSet<Long> {
        return Holder.INSTANCE.selectedNoteList
    }
}
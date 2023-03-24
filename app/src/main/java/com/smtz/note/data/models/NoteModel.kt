package com.smtz.note.data.models

import androidx.lifecycle.LiveData
import com.smtz.note.data.vos.NoteVO

interface NoteModel {
    fun insertNote(note: NoteVO)

    fun insertAllNote(noteList: List<NoteVO>)

    fun getSingleNote(id: Long): LiveData<NoteVO?>?

    fun getAllNotes(): LiveData<List<NoteVO>>?

    fun deleteNote(id: Long)
}
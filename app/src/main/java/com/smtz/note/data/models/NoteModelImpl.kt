package com.smtz.note.data.models

import androidx.lifecycle.LiveData
import com.smtz.note.data.vos.NoteVO

object NoteModelImpl: NoteModel, BaseModel() {

    override fun insertNote(note: NoteVO) {
        mNoteDatabase?.noteDao()?.insertNote(note)
    }

    override fun insertAllNote(noteList: List<NoteVO>) {
        mNoteDatabase?.noteDao()?.insertAllNote(noteList)
    }

    override fun getSingleNote(id: Long): LiveData<NoteVO?>? {
        return mNoteDatabase?.noteDao()?.getSingleNote(id)
    }

    override fun getAllNotes(): LiveData<List<NoteVO>>? {
        return mNoteDatabase?.noteDao()?.getAllNotes()
    }

    override fun deleteNote(id: Long) {
        mNoteDatabase?.noteDao()?.deleteSingleNote(id)
    }
}
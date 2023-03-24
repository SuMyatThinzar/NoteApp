package com.smtz.note.data.models

import android.content.Context
import com.smtz.note.persistence.NoteDatabase

abstract class BaseModel {
    protected var mNoteDatabase: NoteDatabase? = null

    fun initDatabase(context: Context) {   // call from NoteApplication
        mNoteDatabase = NoteDatabase.getDBInstance(context)
    }
}
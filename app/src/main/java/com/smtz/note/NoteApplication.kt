package com.smtz.note

import android.app.Application
import com.smtz.note.data.models.NoteModelImpl

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        NoteModelImpl.initDatabase(applicationContext)
    }
}
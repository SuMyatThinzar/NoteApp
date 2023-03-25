package com.smtz.note.presenters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.smtz.note.data.models.NoteModel
import com.smtz.note.data.models.NoteModelImpl
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.utils.MONTHS_ARRAY
import com.smtz.note.views.CreateNoteView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CreateNotePresenterImpl : CreateNotePresenter, AbstractBasePresenter<CreateNoteView>() {

    private val mNoteModel: NoteModel = NoteModelImpl

    override fun onUiReady(owner: LifecycleOwner, id: Long) {
        if (id != 0L) {
            mNoteModel.getSingleNote(id)?.observe(owner) {
                mView.setUpNoteVO(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTapDone(title: String, content: String, id: Long, date: String) {
        // new note
        if (id == 0L) {
            val newId = System.currentTimeMillis()

            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
            val formattedDate = currentDate.format(formatter)

            if (title == "" || content == "null") {

                if (content == "")
                    mNoteModel.insertNote(NoteVO(title = title, id = newId, date = formattedDate))

                if (title == "")
                    mNoteModel.insertNote(NoteVO(title = "title", content = content, id = newId, date = formattedDate))
            } else {
                mNoteModel.insertNote(NoteVO(title = title, content = content, id = newId, date = formattedDate))
            }
        }
        // edit note
        else {
            if (title == "")
                mNoteModel.insertNote(NoteVO(title = "title", content = content, id = id, date = date))
            else
                mNoteModel.insertNote(NoteVO(title = title, content = content, id = id, date = date))
        }
    }

    private fun replaceMonths(str: String): String {
        var result = str
        MONTHS_ARRAY.forEach { (key, value) ->
            result = result.replace(Regex("\\b$key\\b"), value.toString())
        }
        return result
    }


    override fun onUiReady(owner: LifecycleOwner) {}
}
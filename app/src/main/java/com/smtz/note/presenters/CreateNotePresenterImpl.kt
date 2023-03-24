package com.smtz.note.presenters

import androidx.lifecycle.LifecycleOwner
import com.smtz.note.data.models.NoteModel
import com.smtz.note.data.models.NoteModelImpl
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.views.CreateNoteView

class CreateNotePresenterImpl : CreateNotePresenter, AbstractBasePresenter<CreateNoteView>() {

    private val mNoteModel: NoteModel = NoteModelImpl

    override fun onUiReady(owner: LifecycleOwner, id: Long) {
        if (id != 0L) {
            mNoteModel.getSingleNote(id)?.observe(owner) {
                mView.setUpNoteVO(it)
            }
        }
    }

    override fun onTapDone(title: String, content: String, id: Long) {
        // new note
        if (id == 0L) {
            val newId = System.currentTimeMillis()

            if (title == "" || content == "null") {

                if (content == "")
                    mNoteModel.insertNote(NoteVO(title = title, id = newId))

                if (title == "")
                    mNoteModel.insertNote(NoteVO(title = "title", content = content, id = newId))
            } else {
                mNoteModel.insertNote(NoteVO(title = title, content = content, id = newId))
            }
        }
        // edit note
        else {
            if (title == "")
                mNoteModel.insertNote(NoteVO(title = "title", content = content, id = id))
            else
                mNoteModel.insertNote(NoteVO(title = title, content = content, id = id))
        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}
}
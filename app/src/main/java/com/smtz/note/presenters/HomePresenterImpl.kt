package com.smtz.note.presenters

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.smtz.note.common.NoteSingleton
import com.smtz.note.data.models.NoteModel
import com.smtz.note.data.models.NoteModelImpl
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.utils.mNoteList
import com.smtz.note.views.HomeView

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private var mNoteModel: NoteModel = NoteModelImpl
//    private var mNoteList: List<NoteVO> = listOf()        // changes တွေအကုန် ဒီ copy ထားတဲ့ list ထဲကဟာကိုပဲ change
    private var mSelectedNotes: MutableList<Long> = mutableListOf()

    override fun onTapCreateNote() {
        mView.navigateToCreateNoteActivity(0)
    }

    override fun onUiReady(owner: LifecycleOwner) {
        mNoteModel.getAllNotes()?.observe(owner) { noteList ->

            val mPinnedList: MutableList<NoteVO> = mutableListOf()
            val notes: MutableList<NoteVO> = mutableListOf()

            mPinnedList.clear()
            noteList.forEach {
                if (it.isPinned == true) mPinnedList.add(it)
                else notes.add(it)
                mNoteList.add(it)  // temporary all noteList (mNoteModel.getNoteList() လို့ခေါ်လို့ရပေမယ့် double LiveData ဖြစ်နေလို့)
            }
            mPinnedList.sortByDescending { it.pinTimeStamp }
            mPinnedList.addAll(notes.reversed())
            mView.showNoteList(mPinnedList)
        }
    }

    // Home Screen's
    override fun onTapDeleteNote() {
        NoteSingleton.instance.getSelectedNoteList().forEach {
            mNoteList.forEach { note ->
                if (note.id == it) {
                    mNoteModel.deleteNote(it)
                }
            }
        }
        mView.showError("${NoteSingleton.instance.getSelectedNoteList().count()} note(s) deleted")
        NoteSingleton.instance.cleanup()
    }

    override fun onTapPinNote() {
        NoteSingleton.instance.getSelectedNoteList().forEach {
            mNoteList.forEach { note ->
                if (note.id == it) {

                    if (note.isPinned == false) {
                        note.isPinned = true
                        note.pinTimeStamp = System.currentTimeMillis()
                        mNoteModel.insertNote(note)
                    } else {
                        note.isPinned = false
                        note.pinTimeStamp = 0L
                        mNoteModel.insertNote(note)
                    }
                }
            }
        }
        NoteSingleton.instance.cleanup()
        mView.hideAdditionalSetting()
    }

    // Delegate's
    override fun onTapNote(id: Long) {
        mView.navigateToCreateNoteActivity(id)
    }

    // checked ကို UI မှာပဲ change တာ DB မှာမ change ဘူး but mNoteList မှာတော့ change တယ်
    override fun onLongClickNote() {
        if (NoteSingleton.instance.getSelectedNoteList().isEmpty()) {
            mView.hideAdditionalSetting()
        } else {
            mView.showAdditionalSetting()
        }
    }

    override fun clearSelectedNotes() {
        NoteSingleton.instance.cleanup()
        mView.hideAdditionalSetting()
    }

}
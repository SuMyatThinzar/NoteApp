package com.smtz.note.presenters

import androidx.lifecycle.LifecycleOwner
import com.smtz.note.data.models.NoteModel
import com.smtz.note.data.models.NoteModelImpl
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
//        noteList = mNoteModel.getAllNotes() as MutableLiveData<List<NoteVO>>

        mNoteModel.getAllNotes()?.observe(owner) { noteList ->
            mNoteList = noteList
            mView.showNoteList(noteList)
        }
    }

    // Home Screen's
    override fun onTapDeleteNote() {
        // DB မှာ change တာ new data ပြန်သိမ်းမသိမ်း
//        mNoteModel.insertAllNote(mNoteList)

        mSelectedNotes.forEach {

            mNoteList.forEach { note->
                if (note.id == it) {
                    if (note.checked == true) {
                        mNoteModel.deleteNote(it)
                    }
                }
            }
        }
        mView.showError("${mSelectedNotes.count()} notes deleted")
        mSelectedNotes = mutableListOf()
    }

    // Delegate's
    override fun onTapNote(id: Long) {
        mView.navigateToCreateNoteActivity(id)
    }

    // checked ကို UI မှာပဲ change တာ DB မှာမ change ဘူး but mNoteList မှာတော့ change တယ်
    override fun onLongClickNote(id: Long) {
        // for the first time when mSelectedNote is empty
        if (mSelectedNotes.isEmpty()) {
            mSelectedNotes.add(id)
            mView.showAdditionalSetting()

            mNoteList.forEach {
                if (it.id == id) {
                    it.checked = true
                }
            }

        } else {
            var bool = true

            // check if the note is selected or not
            for (noteId in mSelectedNotes){
                if (noteId == id) {
                    bool = false
                    break
                } else {
                    bool = true
                }
            }

            // if the note is selected insert true false
            if (bool) {
                mSelectedNotes.add(id)
                mNoteList.forEach{
                    if (it.id == id) {
                        it.checked = true
                    }
                }
            } else {
                mSelectedNotes.remove(id)
                mNoteList.forEach{
                    if (it.id == id) {
                        it.checked = false
                    }
                }
            }
        }

        // bind view with new data
        if (mSelectedNotes.isEmpty()) {
            mView.hideAdditionalSetting()
        } else {
            mView.showAdditionalSetting()
        }
    }

    override fun clearSelectedNotes() {
        mSelectedNotes.forEach { id->
            mNoteList.forEach { note->
                if (note.id == id) {
                    if (note.checked == true) {
                        note.checked = false
                    }
                }
            }
        }
        mSelectedNotes = mutableListOf()
        mView.hideAdditionalSetting()
    }

}
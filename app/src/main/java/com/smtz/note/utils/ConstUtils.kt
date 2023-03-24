package com.smtz.note.utils

import androidx.lifecycle.MutableLiveData
import com.smtz.note.data.vos.NoteVO

const val SELECTED_ITEM_SCALE = 0.9f

//var noteList: MutableLiveData<List<NoteVO>> = MutableLiveData<List<NoteVO>>()
var mNoteList: List<NoteVO> = listOf()   // changes တွေအကုန် ဒီ copy ထားတဲ့ list ထဲကဟာကိုပဲ change just for one time active
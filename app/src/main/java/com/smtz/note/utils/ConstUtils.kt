package com.smtz.note.utils

import androidx.lifecycle.MutableLiveData
import com.smtz.note.data.vos.NoteVO

const val SELECTED_ITEM_SCALE = 0.9f

val MONTHS_ARRAY = mapOf<String, Int>("Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6, "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12)

//var noteList: MutableLiveData<List<NoteVO>> = MutableLiveData<List<NoteVO>>()
var mNoteList: List<NoteVO> = listOf()   // changes တွေအကုန် ဒီ copy ထားတဲ့ list ထဲကဟာကိုပဲ change just for one time active
package com.smtz.note.utils

import com.smtz.note.data.vos.NoteVO

const val SELECTED_ITEM_SCALE_SMALL = 0.8f
const val SELECTED_ITEM_SCALE_LARGE = 1f

val MONTHS_ARRAY = mapOf<String, Int>("Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6, "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12)

//var noteList: MutableLiveData<List<NoteVO>> = MutableLiveData<List<NoteVO>>()
var mNoteList: MutableList<NoteVO> = mutableListOf()   // changes တွေအကုန် ဒီ copy ထားတဲ့ list ထဲကဟာကိုပဲ change just for one time active
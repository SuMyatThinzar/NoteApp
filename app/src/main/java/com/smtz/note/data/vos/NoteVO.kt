package com.smtz.note.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. Entity 2. Dao 3. Database
@Entity(tableName = "note")   // Entity တစ်ခုသည် table တစ်ခု | Entity ဆိုတာ PL ထဲကိုသိမ်းမယ့် obj တစ်ခု
data class NoteVO(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    var checked: Boolean? = false,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "pinTimeStamp")
    var pinTimeStamp: Long? = 0L,

    @ColumnInfo(name = "isPinned")
    var isPinned: Boolean? = false,

)
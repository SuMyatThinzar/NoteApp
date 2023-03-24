package com.smtz.note.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smtz.note.data.vos.NoteVO

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteVO?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNote(noteList: List<NoteVO>?)

    @Query("SELECT * FROM note WHERE id= :id")
    fun getSingleNote(id: Long): LiveData<NoteVO?>

    @Query("SELECT * FROM note order by id ASC")
    fun getAllNotes(): LiveData<List<NoteVO>>?

    @Query("DELETE FROM note WHERE id= :id")
    fun deleteSingleNote(id: Long)

    // မလိုဘူး OnConflictStrategy.REPLACE နဲ့ Insert လုပ်ရင် primary key တူရင် auto Replace or Update
//    @Query("UPDATE note Set title= :title, text= :text, id= :id")
//    fun updateNote(title: String, text: String, id: Long)
}
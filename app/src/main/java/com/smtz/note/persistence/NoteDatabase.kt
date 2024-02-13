package com.smtz.note.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.persistence.daos.NoteDao

// 1. Entity 2. Dao 3. Database
@Database(entities = [NoteVO::class], version = 8, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    companion object{
        private const val DB_NAME = "NOTE4ALL"

        var dbInstance: NoteDatabase? = null

        fun getDBInstance(context: Context): NoteDatabase? {  // call from BaseModel/ModelImpl

            when(dbInstance) {
                null -> dbInstance = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dbInstance
        }
    }

    abstract fun noteDao(): NoteDao
}
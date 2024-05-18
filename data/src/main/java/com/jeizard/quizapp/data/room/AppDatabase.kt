package com.jeizard.quizapp.data.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jeizard.quizapp.data.room.models.notes.dao.NoteDao
import com.jeizard.quizapp.data.room.models.notes.entity.NoteDBEntity

@Database(
    entities = [NoteDBEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "notes"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as AppDatabase
        }
    }
}

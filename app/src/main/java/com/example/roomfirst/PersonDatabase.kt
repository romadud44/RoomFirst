package com.example.roomfirst

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun getPersonDao():PersonDao

    companion object{
        private var INSTANCE: PersonDatabase? = null
        fun getDatabase(context: Context) : PersonDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "person_database"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}
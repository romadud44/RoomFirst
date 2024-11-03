package com.example.roomfirst

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    @Insert
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("SELECT * from persons_table ORDER BY id ASC")
    fun getAllPersons():List<Person>

    @Query("DELETE FROM persons_table")
    fun deleteAll()
}
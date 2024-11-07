package com.example.roomfirst

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons_table")
data class Person(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lastname") var lastName: String,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "timesTamp") var timesTamp: String
) {
    @PrimaryKey(autoGenerate = true)
 var id = 0
}
package com.example.roomfirst

import androidx.lifecycle.LiveData

class PersonRepository(private val personDao: PersonDao) {
    val persons: LiveData<List<Person>> = personDao.getAllPersons()
    suspend fun insert(person: Person){
        personDao.insert(person)
    }
    suspend fun delete(person: Person){
        personDao.delete(person)
    }
}
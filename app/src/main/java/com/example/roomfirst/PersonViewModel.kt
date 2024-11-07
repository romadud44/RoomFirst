package com.example.roomfirst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PersonRepository
    val persons: LiveData<List<Person>>

    init {
        val dao = PersonDatabase.getDatabase(application).getPersonDao()
        repository = PersonRepository(dao)
        persons = repository.persons
    }
    fun delete(person: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(person)
    }
    fun insertPerson(person: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(person)
    }
}
package com.example.roomfirst

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomfirst.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@Suppress("DeferredResultUnused")
class MainActivity : AppCompatActivity(), PersonAdapter.PersonClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PersonViewModel

    //    var db: PersonDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerViewRV.layoutManager = LinearLayoutManager(this)
        val adapter = PersonAdapter(this, this)
        binding.recyclerViewRV.adapter = adapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PersonViewModel::class.java]
        viewModel.persons.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }

//        db = PersonDatabase.getDatabase(this)
//        readDatabase(db!!)
    }

    override fun onResume() {
        super.onResume()
//        binding.saveBTN.setOnClickListener {
//            val person = Person(
//                binding.nameET.text.toString(),
//                binding.lastNameET.text.toString(),
//                binding.phoneET.text.toString()
//            )
//            addPerson(db!!, person)
//            readDatabase(db!!)
//
//            clearFields()
//        }
    }

    private fun clearFields() {
        binding.nameET.text.clear()
        binding.lastNameET.text.clear()
        binding.phoneET.text.clear()
    }

//    @OptIn(DelicateCoroutinesApi::class)
//    private fun addPerson(db: PersonDatabase, person: Person) = GlobalScope.async {
//        db.getPersonDao().insert(person)
//    }
//
//    @OptIn(DelicateCoroutinesApi::class)
//    private fun readDatabase(db: PersonDatabase) = GlobalScope.async {
//        binding.resultTV.text = ""
//        val list = db.getPersonDao().getAllPersons()
//        list.forEach { i -> binding.resultTV.append(i.name + " " + i.lastName + " " + i.phone + "\n") }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                finishAndRemoveTask()
                finishAffinity()
                finish()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(person: Person) {
        viewModel.delete(person)
        Toast.makeText(this, "${person.name} удалён", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun formatMilliseconds(time: Long): String {
        val timeFormat = SimpleDateFormat("EEE, HH:mm")
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+03")
        return timeFormat.format(Date(time))
    }

    fun saveData(view: View) {
        val timestamp = formatMilliseconds(Date().time)
        if (binding.nameET.text.isNotEmpty()){
            viewModel.insertPerson(Person(binding.nameET.text.toString(),
                binding.lastNameET.text.toString(),
                binding.phoneET.text.toString(), timestamp))
            Toast.makeText(this, "${binding.nameET.text}, $timestamp добавлен", Toast.LENGTH_LONG).show()
        }
        clearFields()
    }
}
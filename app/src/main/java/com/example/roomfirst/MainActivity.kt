package com.example.roomfirst

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roomfirst.databinding.ActivityMainBinding
import kotlinx.coroutines.*

@Suppress("DeferredResultUnused")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var db: PersonDatabase? = null
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


        db = PersonDatabase.getDatabase(this)
        readDatabase(db!!)
    }

    override fun onResume() {
        super.onResume()
        binding.saveBTN.setOnClickListener {
            val person = Person(
                binding.nameET.text.toString(),
                binding.lastNameET.text.toString(),
                binding.phoneET.text.toString()
            )
            addPerson(db!!, person)
            readDatabase(db!!)

            clearFields()
        }
    }

    private fun clearFields() {
        binding.nameET.text.clear()
        binding.lastNameET.text.clear()
        binding.phoneET.text.clear()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addPerson(db: PersonDatabase, person: Person) = GlobalScope.async {
        db.getPersonDao().insert(person)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun readDatabase(db: PersonDatabase) = GlobalScope.async {
        binding.resultTV.text = ""
        val list = db.getPersonDao().getAllPersons()
        list.forEach { i -> binding.resultTV.append(i.name + " " + i.lastName + " " + i.phone + "\n") }
    }

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
}
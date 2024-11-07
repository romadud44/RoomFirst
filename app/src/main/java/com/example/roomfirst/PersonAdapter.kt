package com.example.roomfirst

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(private val context: Context, private val listener: PersonClickListener) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    val persons = ArrayList<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Person>) {
        persons.clear()
        persons.addAll(newList)
        notifyDataSetChanged()
    }

    inner class PersonViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
                private val itemNameTV: TextView = itemView.findViewById(R.id.itemNameTV)
                private val itemLastNameTV: TextView = itemView.findViewById(R.id.itemLastNameTV)
                private val itemPhoneTV: TextView = itemView.findViewById(R.id.itemPhoneTV)
                private val itemTimesTampTV: TextView = itemView.findViewById(R.id.itemTimesTampTV)
                val itemIconDeleteIV: ImageView = itemView.findViewById(R.id.itemIconDeleteIV)
                fun bind(person: Person) {
                    itemNameTV.text = person.name
                    itemLastNameTV.text = person.lastName
                    itemPhoneTV.text = person.phone
                    itemTimesTampTV.text = person.timesTamp
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val viewHolder = PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        viewHolder.itemIconDeleteIV.setOnClickListener{
            listener.onItemClicked(persons[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentPerson = persons[position]
        holder.bind(currentPerson)

    }

    interface PersonClickListener{
        fun onItemClicked(person: Person)
        abstract fun formatMilliseconds(time: Long): String
    }
}
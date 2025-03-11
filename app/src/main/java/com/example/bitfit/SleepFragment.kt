package com.example.bitfit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "SleepFragment"

class SleepFragment : Fragment() {
    private var sleeps = mutableListOf<Sleep>()
    private lateinit var sleepRecyclerView: RecyclerView
    private lateinit var sleepAdapter: SleepAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep, container, false)
        val layoutManager = LinearLayoutManager(context)

        //Get sleeps from MainActivity, update adapter
        sleeps = (activity as MainActivity).getDB()

        sleepRecyclerView = view.findViewById(R.id.sleepRv)
        sleepRecyclerView.layoutManager = layoutManager
        sleepRecyclerView.setHasFixedSize(true)
        sleepAdapter = SleepAdapter(sleeps)
        sleepRecyclerView.adapter = sleepAdapter


        val hoursInput = view.findViewById<EditText>(R.id.hoursInput)
        val minutesInput = view.findViewById<EditText>(R.id.minutesInput)
        val dateInput = view.findViewById<EditText>(R.id.dateInput)
        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            //Add to list
            val newSleep = Sleep(dateInput.text.toString(), hoursInput.text.toString(), minutesInput.text.toString())
            addToList(newSleep)
            //Add to db
            (activity as MainActivity).addToDB(newSleep)
        }
        return view
    }

    fun makeList(mappedList: List<Sleep>) {
        sleeps.clear()
        sleeps.addAll(mappedList)
        sleepAdapter.notifyDataSetChanged()
    }

    fun addToList(newSleep: Sleep) {
        sleeps.add(newSleep)
        sleepAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): SleepFragment {
            return SleepFragment()
        }
    }
}

//Get data from the database on startup
//        lifecycleScope.launch{
//            (application as SleepApplication).db.sleepDao().getAll().collect { databaseList ->
//                databaseList.map { entity ->
//                    Sleep(
//                        entity.date,
//                        entity.hours,
//                        entity.minutes
//                    )
//                }.also {mappedList ->
//                    makeList(mappedList)
//                }
//            }
//        }

//        Add data to the database
//        val hoursInput = findViewById<EditText>(R.id.hoursInput)
//        val minutesInput = findViewById<EditText>(R.id.minutesInput)
//        val dateInput = findViewById<EditText>(R.id.dateInput)
//        val addButton = findViewById<Button>(R.id.addButton)
//        addButton.setOnClickListener {
//            //Add to list
//            val newSleep = Sleep(dateInput.text.toString(), hoursInput.text.toString(), minutesInput.text.toString())
//            addToList(newSleep)
//            //Add to db
//            lifecycleScope.launch(IO) {
//            (application as SleepApplication).db.sleepDao().deleteAll()
//            (application as SleepApplication).db.sleepDao().insertAll(sleeps.map {
//                SleepEntity(
//                    date = it.date,
//                    hours = it.hours,
//                    minutes = it.minutes
//                )
//            })}
//        }

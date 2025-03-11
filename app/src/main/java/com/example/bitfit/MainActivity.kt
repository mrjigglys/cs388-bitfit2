package com.example.bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.bitfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sleeps = mutableListOf<Sleep>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Get from DB
        lifecycleScope.launch{
            (application as SleepApplication).db.sleepDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Sleep(
                        entity.date,
                        entity.hours,
                        entity.minutes
                    )
                }.also {mappedList ->
                    sleeps.clear()
                    sleeps.addAll(mappedList)
                }
            }
        }

        val fragmentManager: FragmentManager = supportFragmentManager
        //Fragments
        val sleepFragment: Fragment = SleepFragment()
        val summaryFragment: Fragment = SummaryFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when(item.itemId) {
                R.id.nav_sleep -> fragment = sleepFragment
                R.id.nav_summary -> fragment = summaryFragment
            }
            replaceFragment(fragment)
            true
        }
        bottomNavigationView.selectedItemId = R.id.nav_sleep
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun addToDB(newSleep: Sleep) {
        lifecycleScope.launch(IO) {
            (application as SleepApplication).db.sleepDao().deleteAll()
            (application as SleepApplication).db.sleepDao().insertAll(sleeps.map {
                SleepEntity(
                    date = it.date,
                    hours = it.hours,
                    minutes = it.minutes
                )
            })}
    }

    fun getDB() : MutableList<Sleep>{
        return sleeps
    }
}
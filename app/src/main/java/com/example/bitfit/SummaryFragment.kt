package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

class SummaryFragment : Fragment() {
    private var sleeps = mutableListOf<Sleep>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        val layoutManager = LinearLayoutManager(context)

        val totalHoursText: TextView = view.findViewById(R.id.totalHoursText)
        val averageHoursText: TextView = view.findViewById(R.id.averageHoursText)

        sleeps = (activity as MainActivity).getDB()

        // Get hours and minutes
        var hSum = 0.0
        var mSum = 0.0
        for (item in sleeps) {
            if (!item.hours.isNullOrBlank())
                hSum+=item.hours.toInt()
            if (!item.minutes.isNullOrBlank())
                mSum+=item.minutes.toInt()
        }
        // Convert minutes to hours
        hSum+=(mSum/60)
        var hAvg = hSum/sleeps.size
        totalHoursText.text = "Total Hours: " + hSum.toString()
        averageHoursText.text = "Average Hours: " + hAvg.toString()

        return view
    }

    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}
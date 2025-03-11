package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

class SummaryFragment : Fragment() {


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

        val totalHoursText: View = view.findViewById(R.id.totalHoursText)
        val averageHoursText: View = view.findViewById(R.id.averageHoursText)
        // Get info from database and insert into textViews

        return view
    }

    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}
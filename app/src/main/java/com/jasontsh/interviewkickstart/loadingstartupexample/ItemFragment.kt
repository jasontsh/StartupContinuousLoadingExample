package com.jasontsh.interviewkickstart.loadingstartupexample

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jasontsh.interviewkickstart.loadingstartupexample.Constants.DELETE_MAX_COUNT
import com.jasontsh.interviewkickstart.loadingstartupexample.Constants.NUMBER_OF_ITEMS

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val sharedPreferences =
            requireContext().getSharedPreferences(
                getString(R.string.preference_key),
                Context.MODE_PRIVATE
            )
        val stringSet = sharedPreferences.getStringSet(Constants.DATA_KEY, setOf())
        checkNotNull(stringSet)
        var doubleList : List<Double> = stringSet.toList().map { s -> s.toDouble() }
        val recyclerView: RecyclerView = view.findViewById(R.id.list)
        val failureTextView: TextView = view.findViewById(R.id.fail_tv)
        if (doubleList.size > DELETE_MAX_COUNT) {
            doubleList = doubleList.subList(NUMBER_OF_ITEMS, doubleList.size)
            val finalStringSet = doubleList.map { d -> d.toString() }.toSet()
            sharedPreferences.edit().putStringSet(Constants.DATA_KEY, finalStringSet).apply()
        }
        // Set the adapter
        if (doubleList.isEmpty()) {
            failureTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(doubleList)
            }
        }
        return view
    }
}
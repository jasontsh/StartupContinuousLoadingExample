package com.jasontsh.interviewkickstart.loadingstartupexample

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.jasontsh.interviewkickstart.loadingstartupexample.Constants.DATA_KEY
import com.jasontsh.interviewkickstart.loadingstartupexample.Constants.NUMBER_OF_ITEMS
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LoadingService : Service() {
    private var started = false
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (started) {
            return START_NOT_STICKY
        }
        started = true
        val executorService = Executors.newScheduledThreadPool(1)
        executorService.scheduleAtFixedRate({
            val sharedPreferences =
                getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
            val stringSet = sharedPreferences.getStringSet(DATA_KEY, setOf())
            checkNotNull(stringSet)
            if (stringSet.size > Constants.FULL_LIST) {
                return@scheduleAtFixedRate
            }
            val doubleList : List<Double> = stringSet.toList().map { s -> s.toDouble() }
            val list = ArrayList<Double>(NUMBER_OF_ITEMS)
            while (list.size < NUMBER_OF_ITEMS) {
                val failure = Math.random() > 0.999999999
                if (failure) {
                    return@scheduleAtFixedRate
                }
                val i = Math.random()
                if (i > 0.999995) {
                    list.add(i)
                }
            }
            val finalList = list + doubleList
            val finalStringSet = finalList.map { d -> d.toString() }.toSet()
            sharedPreferences.edit().putStringSet(DATA_KEY, finalStringSet).apply()
        }, 0, 30, TimeUnit.SECONDS)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        started = false
    }
}
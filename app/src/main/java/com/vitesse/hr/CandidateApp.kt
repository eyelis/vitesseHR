package com.vitesse.hr

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CandidateApp  : Application(){
    init {
        println("Started")
    }
}
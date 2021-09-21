package br.com.part.codelabs

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class CdlApplication: Application() {

    companion object{
        lateinit var instance : CdlApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)
    }
}
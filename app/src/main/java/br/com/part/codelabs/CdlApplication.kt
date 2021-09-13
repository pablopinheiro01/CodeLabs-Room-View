package br.com.part.codelabs

import android.app.Application

class CdlApplication: Application() {

    companion object{
        lateinit var instance : CdlApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
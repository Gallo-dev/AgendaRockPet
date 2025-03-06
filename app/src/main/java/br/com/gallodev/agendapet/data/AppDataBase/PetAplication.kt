package br.com.gallodev.agendapet.data.AppDataBase

import android.app.Application

class PetApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
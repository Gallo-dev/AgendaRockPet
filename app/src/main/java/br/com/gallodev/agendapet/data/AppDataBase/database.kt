package br.com.gallodev.agendapet.data.AppDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gallodev.agendapet.data.model.Cliente

@Database(entities = [Cliente::class], version = 2, exportSchema = false)
abstract class  AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agendapet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
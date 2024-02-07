package es.otm.myproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [Cat::class],
    version = 1
)
abstract class CatDB : RoomDatabase() {
    abstract fun catDAO(): CatDao

    companion object{
        private const val DATABASE_NAME = "CatDB"

        private var instance : CatDB? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): CatDB{
            synchronized(this){
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CatDB::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return instance!!
        }
    }
}
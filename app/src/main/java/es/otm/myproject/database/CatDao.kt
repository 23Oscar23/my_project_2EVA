package es.otm.myproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {
    @Query("SELECT * FROM cat GROUP BY breed")
    suspend fun getAll(): MutableList<Cat>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Query("DELETE FROM cat")
    suspend fun deleteAll()
}
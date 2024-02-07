package es.otm.myproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cat")
data class Cat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("breed")
    val breed: String,

    @ColumnInfo("description")
    val description: String
)

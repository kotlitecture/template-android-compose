package app.datasource.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String? = null,
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
)
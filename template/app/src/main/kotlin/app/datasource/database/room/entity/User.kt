package app.datasource.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents a User entity in the database.
 * <p>
 * This entity is provided as an example to define custom entities in Room.
 * Developers should create their own entities tailored to their application's requirements.
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String? = null,
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
)
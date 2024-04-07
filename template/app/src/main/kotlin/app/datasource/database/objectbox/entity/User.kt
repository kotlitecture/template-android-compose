package app.datasource.database.objectbox.entity

import app.datasource.database.objectbox.BoxEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Represents a user entity in the ObjectBox database.
 *
 * This class is an example of how to define entities in ObjectBox.
 *
 * @param id The unique identifier for the user.
 * @param firstName The first name of the user.
 * @param lastName The last name of the user.
 */
@Entity
data class User(
    @Id
    override var id: Long = 0L,
    var firstName: String? = null,
    var lastName: String? = null
) : BoxEntity()
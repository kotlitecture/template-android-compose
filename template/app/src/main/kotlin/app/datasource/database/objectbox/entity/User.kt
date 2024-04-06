package app.datasource.database.objectbox.entity

import app.datasource.database.objectbox.BoxEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id
    override var id: Long = 0L,
    var firstName: String? = null,
    var lastName: String? = null
) : BoxEntity()
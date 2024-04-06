package app.datasource.database.objectbox.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id
    var id: Long,
    var firstName: String?,
    var lastName: String?
)
package app.datasource.database.objectbox.dao

import app.datasource.database.objectbox.BoxDao
import app.datasource.database.objectbox.entity.User
import io.objectbox.Box

/**
 * Represents a DAO (Data Access Object) for interacting with User entities.
 *
 * @param box The ObjectBox box containing User entities.
 */
class UserDao(box: Box<User>) : BoxDao<User>(box)
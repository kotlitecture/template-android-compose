package app.datasource.database.objectbox.dao

import app.datasource.database.objectbox.BoxDao
import app.datasource.database.objectbox.entity.User
import io.objectbox.Box

class UserDao(box: Box<User>) : BoxDao<User>(box)
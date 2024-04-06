package app.datasource.database.objectbox

import app.datasource.database.objectbox.entity.User
import dagger.hilt.android.testing.HiltAndroidTest
import java.util.UUID
import javax.inject.Inject

@HiltAndroidTest
class UserDaoTest : BoxDaoTest<User>() {

    @Inject
    lateinit var objectboxSource: AppObjectBoxSource

    override fun createEntity(): User = User(
        firstName = UUID.randomUUID().toString(),
        lastName = UUID.randomUUID().toString(),
    )

    override fun dao(): BoxDao<User> = objectboxSource.userDao


}
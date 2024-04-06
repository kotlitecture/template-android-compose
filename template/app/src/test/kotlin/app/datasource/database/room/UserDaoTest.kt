package app.datasource.database.room

import app.BaseHiltUnitTest
import app.datasource.database.room.entity.User
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UserDaoTest : BaseHiltUnitTest() {

    @Inject
    lateinit var roomSource: AppRoomSource

    @Test
    fun create() = performTest {
        val dao = roomSource.userDao
        val user = User()
        dao.create(user)
        Assert.assertNotEquals(0L, user.id)
    }

}
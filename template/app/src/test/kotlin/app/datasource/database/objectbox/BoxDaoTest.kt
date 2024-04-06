package app.datasource.database.objectbox

import app.BaseHiltUnitTest
import org.junit.Assert
import org.junit.Test

abstract class BoxDaoTest<T : BoxEntity> : BaseHiltUnitTest() {

    abstract fun createEntity(): T

    abstract fun dao(): BoxDao<T>

    @Test
    fun createOrUpdate() = performTest {
        val dao = dao()
        val entity = createEntity()
        dao.createOrUpdate(entity)
        Assert.assertNotEquals(0L, entity.id)
    }

}
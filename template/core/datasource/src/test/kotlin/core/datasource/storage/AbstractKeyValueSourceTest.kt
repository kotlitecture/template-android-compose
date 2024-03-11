package core.datasource.storage

import core.testing.BaseAndroidHiltUnitTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

abstract class AbstractKeyValueSourceTest : BaseAndroidHiltUnitTest() {

    private val cache: IKeyValueSource by lazy { provider() }

    abstract fun provider(): IKeyValueSource

    @Test
    fun `read not existing value`() = performTest {
        val key = "key"
        assertNull(cache.read(key, Int::class.java))
    }

    @Test
    fun `remove existing value`() = performTest {
        val key = "key"

        // for given saved value
        cache.save(key, 111, Int::class.java)
        assertEquals(111, cache.read(key, Int::class.java))

        // when it is deleted
        cache.remove(key)

        // then it can not be read
        assertNull(cache.read(key, Int::class.java))
    }

    @Test
    fun `clear all values`() = performTest {
        val key1 = "key1"
        val key2 = "key2"

        // for given saved values
        cache.save(key1, 111, Int::class.java)
        assertEquals(111, cache.read(key1, Int::class.java))
        cache.save(key2, 222, Int::class.java)
        assertEquals(222, cache.read(key2, Int::class.java))

        // when delete all values
        cache.clear()

        // then no values can be read
        assertNull(cache.read(key1, Int::class.java))
        assertNull(cache.read(key2, Int::class.java))
    }

    @Test
    fun `save primitive`() = performTest {
        val key = "key"

        // when save primitive
        cache.save(key, 111, Int::class.java)

        // then it can be read
        assertEquals(111, cache.read(key, Int::class.java))
    }

    @Test
    fun `save object`() = performTest {
        val key = "key"

        // when save object
        cache.save(key, TestObject("name"), TestObject::class.java)

        // then it can be read
        assertEquals(TestObject("name"), cache.read(key, TestObject::class.java))
    }

    data class TestObject(
        val name: String
    )

}
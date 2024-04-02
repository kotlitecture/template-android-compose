package core.data.datasource.storage.keyvalue

import core.data.serialization.GsonStrategy
import core.data.serialization.JsonStrategy
import core.data.serialization.NoSerializationStrategy
import core.testing.BaseAndroidUnitTest
import kotlinx.serialization.Serializable
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

abstract class BaseKeyValueSourceTest : BaseAndroidUnitTest() {

    private val source: KeyValueSource by lazy { create() }

    abstract fun create(): KeyValueSource

    @Test
    fun `read not existing value`() = performTest {
        val key = "key"
        assertNull(source.read(key, NoSerializationStrategy.create()))
    }

    @Test
    fun `remove existing value`() = performTest {
        val key = "key"

        // for given saved value
        source.save(key, 111, NoSerializationStrategy.create())
        assertEquals(111, source.read(key, NoSerializationStrategy.create()))
        // when it is deleted
        val prev = source.remove<Int>(key, NoSerializationStrategy.create())
        // then it can not be read
        assertNull(source.read(key, NoSerializationStrategy.create()))
        assertEquals(111, prev)
    }

    @Test
    fun `clear all values`() = performTest {
        val key1 = "key1"
        val key2 = "key2"

        // for given saved values
        source.save(key1, 111, NoSerializationStrategy.create())
        assertEquals(111, source.read(key1, NoSerializationStrategy.create()))
        source.save(key2, 222, NoSerializationStrategy.create())
        assertEquals(222, source.read(key2, NoSerializationStrategy.create()))

        // when delete all values
        source.clear()

        // then no values can be read
        assertNull(source.read(key1, NoSerializationStrategy.create()))
        assertNull(source.read(key2, NoSerializationStrategy.create()))
    }

    @Test
    fun `save primitive`() = performTest {
        val key = "key"

        // when save primitive
        source.save(key, 111, NoSerializationStrategy.create())

        // then it can be read
        assertEquals(111, source.read(key, NoSerializationStrategy.create()))
    }

    @Test
    fun `save object`() = performTest {
        val key = "key"

        // when save object
        source.save(key, TestObject("name"), JsonStrategy.create(TestObject.serializer()))

        // then it can be read
        assertEquals(TestObject("name"), source.read(key, GsonStrategy.create()))
    }

    @Serializable
    data class TestObject(
        val name: String
    )

}
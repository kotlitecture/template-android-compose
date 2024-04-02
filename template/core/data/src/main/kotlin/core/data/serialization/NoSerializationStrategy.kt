package core.data.serialization

/**
 * A [SerializationStrategy] implementation that does not actually perform serialization and throws an exception instead.
 *
 * @param T The type of object.
 * @property type The class representing the type of object.
 */
data class NoSerializationStrategy<T>(private val type: Class<T>) : SerializationStrategy<T> {

    override fun toObject(from: String): T? {
        throw IllegalStateException("use another SerializationStrategy to perform serialization of type $type")
    }

    override fun toString(from: T): String? {
        throw IllegalStateException("use another SerializationStrategy to perform serialization of type $type")
    }

    override fun getType(): Class<T> {
        return type
    }

    companion object {
        inline fun <reified D> create(): SerializationStrategy<D> = NoSerializationStrategy(D::class.java)
    }

}
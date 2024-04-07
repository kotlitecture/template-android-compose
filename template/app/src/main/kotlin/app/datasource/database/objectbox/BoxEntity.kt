package app.datasource.database.objectbox

/**
 * This abstract class represents a base entity for ObjectBox entities.
 *
 * Subclasses should extend this class to define their own entities.
 */
abstract class BoxEntity {

    /**
     * The unique identifier for the entity.
     */
    abstract var id: Long

}
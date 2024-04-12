package app.datasource.database.objectbox

import android.app.Application
import app.datasource.database.objectbox.dao.UserDao
import app.datasource.database.objectbox.entity.MyObjectBox
import app.datasource.database.objectbox.entity.User

/**
 * This class represents a source for accessing the ObjectBox database.
 *
 * It provides access to all underlying DAO objects as well.
 *
 * @property app The application instance.
 * @property databaseName The name of the database.
 */
class AppObjectBoxSource(
    private val app: Application,
    private val databaseName: String = "db"
) {

    private val store by lazy {
        MyObjectBox.builder()
            .androidContext(app)
            .name(databaseName)
            .build()
    }

    /**
     * Retrieves the UserDao for interacting with the User entity.
     *
     * @return The UserDao instance.
     */
    val userDao by lazy { UserDao(store.boxFor(User::class.java)) }

    /**
     * Executes a transaction on the ObjectBox database.
     *
     * @param <R> The type of the result.
     * @param block The block of code to execute within the transaction.
     * @return The result of the transaction.
     */
    fun <R> withTransaction(block: () -> R): R = store.callInTx(block)

}
package app.datasource.database.objectbox

import android.app.Application
import app.datasource.database.objectbox.dao.UserDao
import app.datasource.database.objectbox.entity.MyObjectBox
import app.datasource.database.objectbox.entity.User

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

    val userDao by lazy { UserDao(store.boxFor(User::class.java)) }

    fun <R> withTransaction(block: () -> R): R = store.callInTx(block)

}
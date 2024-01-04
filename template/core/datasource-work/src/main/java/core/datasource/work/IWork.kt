package core.datasource.work

import core.datasource.work.model.WorkModel
import core.datasource.work.model.WorkState
import core.data.state.StoreObject

interface IWork<O> {

    fun getId(): String

    fun getType(): String

    fun getState(): WorkState

    fun getParentId(): String?

    fun getStateReason(): String?

    fun getDataStore(): StoreObject<Any>

    fun getModelStore(): StoreObject<WorkModel>

}
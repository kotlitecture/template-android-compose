package core.datasource.work.model

enum class WorkState(
    val id: String,
    val terminal: Boolean
) {

    Processing("processing", false),
    Preparing("preparing", false),
    Analyzing("analyzing", false),
    Waiting("waiting", false),
    Created("created", false),
    Started("started", false),
    Paused("paused", false),
    Resumed("resumed", false),
    Completed("completed", true),
    Canceled("canceled", true),
    Error("error", true);

    companion object {
        private val byId = values().associateBy { it.id }
        fun by(id: String?) = byId[id] ?: Created

        val Active = entries.filter { !it.terminal }
        val History = entries.filter { it.terminal }
        val Pending = Active.minus(Paused)
    }

}
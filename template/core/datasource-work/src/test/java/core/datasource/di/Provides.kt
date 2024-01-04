package core.datasource.di

import android.app.Application
import core.datasource.work.IWorkConfig
import core.datasource.work.IWorkSource
import core.datasource.work.WorkSource
import core.datasource.work.dao.InMemoryDao
import core.datasource.work.worker.HelloUserGroupWorker
import core.datasource.work.worker.HelloUserWorker
import core.essentials.cache.impl.MemoryCacheSource
import core.essentials.misc.extensions.launchGlobalAsync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkSourceProvides {

    @Provides
    @Singleton
    fun work(app: Application): IWorkSource {
        val source = WorkSource(
            workers = listOf(
                HelloUserWorker(),
                HelloUserGroupWorker()
            ),
            cacheSource = MemoryCacheSource(),
            dao = InMemoryDao(),
            app = app
        )
        launchGlobalAsync("work_queue") {
            source.queue()
                .collect { work ->
                    launchGlobalAsync("work_${work.getId()}", false) {
                        work.execute()
                    }
                }
        }
        return source
    }

    @Provides
    @Singleton
    fun workConfig(app: Application): IWorkConfig = object : IWorkConfig {
        override fun getNotificationTitle(activeWorks: Int): String {
            return activeWorks.toString()
        }

        override fun getNotificationText(activeWorks: Int): String? {
            return null
        }

        override fun getNotificationIcon(): Int {
            return 0
        }

        override fun getChannelName(): String {
            return "channel"
        }
    }

}
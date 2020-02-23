package com.example.krokodyl.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.krokodyl.model.KrokodylDatabase
import com.example.krokodyl.repository.CategoryRepository
import retrofit2.HttpException

// refresh data from API once a day
class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = KrokodylDatabase.getInstance(applicationContext).categoryDatabaseDao
        var repository: CategoryRepository = CategoryRepository(database)
         try {
            repository.refreshCategory()
            return Result.success()
        }catch (e: HttpException){
            return Result.failure()
        }
    }
}
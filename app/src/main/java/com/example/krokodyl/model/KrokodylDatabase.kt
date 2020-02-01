package com.example.krokodyl.model

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseCategory::class], version = 2,  exportSchema = false)
@TypeConverters( Converters::class)
abstract class KrokodylDatabase : RoomDatabase (){

    abstract val categoryDatabaseDao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: KrokodylDatabase? = null

        fun getInstance(context: Context): KrokodylDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KrokodylDatabase::class.java,
                        "Krokodyl_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}

package com.example.marvel.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvel.characters.data.model.Result
import com.example.marvel.core.room.converters.ThumbnailTypeConverter

/**
 * MarvelDatabase class contains the database holder and serves as the
 * main access point for the underlying connection
 */
@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(ThumbnailTypeConverter::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun getMarvelDao(): MarvelDao

    /**
     * Creates a static singleton synchronized Room DB object
     */
    companion object {
        @Volatile
        private var INSTANCE: MarvelDatabase? = null

        fun getDatabase(context: Context): MarvelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarvelDatabase::class.java,
                    "marvel_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
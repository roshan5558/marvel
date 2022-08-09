package com.example.marvel.core.room

import androidx.room.*
import com.example.marvel.characters.data.model.Result
import io.reactivex.Single

/**
 * MarvelDao class defining the methods that access the local database
 */
@Dao
interface MarvelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(characters: List<Result>): Single<List<Long>>

    @Update
    fun update(characters: Result): Single<Int>

    @Query("select * from characters_table")
    fun getAllCharacters(): Single<List<Result>>
}
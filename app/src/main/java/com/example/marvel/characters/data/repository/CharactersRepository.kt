package com.example.marvel.characters.data.repository

import com.example.marvel.BuildConfig
import com.example.marvel.characters.data.api.CharactersService
import com.example.marvel.characters.data.model.Result
import com.example.marvel.core.api.ApiHelper
import com.example.marvel.core.room.MarvelDao
import com.example.marvel.core.utils.Utils
import io.reactivex.Single

/**
 * CharactersRepository class defining the methods that access the server or local database
 */
class CharactersRepository(private val mMarvelDao: MarvelDao) {

    val allCharactersFromDb: Single<List<Result>> = mMarvelDao.getAllCharacters()

    fun insertAllCharacters(characters: List<Result>): Single<List<Long>> {
        return mMarvelDao.insert(characters)
    }

    fun updateCharacters(characters: Result): Single<Int> {
        return mMarvelDao.update(characters)
    }

    fun getAllCharacters() =
        ApiHelper.getApiHelperInstance().create(CharactersService::class.java).getCharacters(
            1,
            BuildConfig.API_PUBLIC_KEY,
            Utils.getMd5(1.toString() + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY)
        )
}
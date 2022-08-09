package com.example.marvel.characterdetails.data.repository

import com.example.marvel.BuildConfig
import com.example.marvel.characterdetails.data.api.CharacterDetailsService
import com.example.marvel.core.api.ApiHelper
import com.example.marvel.core.utils.Utils

/**
 * CharacterDetailsRepository class defining the methods that access the server or local database
 */
class CharacterDetailsRepository {

    fun getCharacterDetails(characterId: Int) =
        ApiHelper.getApiHelperInstance().create(CharacterDetailsService::class.java)
            .getCharacterDetails(
                characterId,
                1,
                BuildConfig.API_PUBLIC_KEY,
                Utils.getMd5(1.toString() + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY)
            )

    fun getCharacterComics(characterId: Int) =
        ApiHelper.getApiHelperInstance().create(CharacterDetailsService::class.java)
            .getCharacterComics(
                characterId,
                1,
                BuildConfig.API_PUBLIC_KEY,
                Utils.getMd5(1.toString() + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY)
            )

    fun getCharacterSeries(characterId: Int) =
        ApiHelper.getApiHelperInstance().create(CharacterDetailsService::class.java)
            .getCharacterSeries(
                characterId,
                1,
                BuildConfig.API_PUBLIC_KEY,
                Utils.getMd5(1.toString() + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY)
            )
}
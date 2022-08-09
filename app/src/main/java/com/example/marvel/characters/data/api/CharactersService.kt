package com.example.marvel.characters.data.api

import com.example.marvel.characters.data.model.Characters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * CharactersService class defining the methods that access the server
 */
interface CharactersService {

    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<Characters>
}
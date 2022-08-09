package com.example.marvel.characterdetails.data.api

import com.example.marvel.characterdetails.data.model.CharacterComics
import com.example.marvel.characterdetails.data.model.CharacterDetails
import com.example.marvel.characterdetails.data.model.CharacterSeries
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * CharacterDetailsService class defining the methods that access the server
 */
interface CharacterDetailsService {

    @GET("characters/{characterId}")
    fun getCharacterDetails(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<CharacterDetails>

    @GET("characters/{characterId}/comics")
    fun getCharacterComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<CharacterComics>

    @GET("characters/{characterId}/series")
    fun getCharacterSeries(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<CharacterSeries>
}
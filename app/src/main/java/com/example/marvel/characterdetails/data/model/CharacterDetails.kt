package com.example.marvel.characterdetails.data.model

import com.squareup.moshi.Json

data class CharacterDetails(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val data: Data
)

data class Data(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<Result>
)

data class Result(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "thumbnail") val thumbnail: Thumbnail,
    @Json(name = "comics") val comics: ComicsDetails,
    @Json(name = "series") val series: SeriesDetails
)

data class Thumbnail(
    @Json(name = "path") val path: String,
    @Json(name = "extension") val extension: String
)

data class ComicsDetails(
    @Json(name = "available") val available: Int,
    @Json(name = "collectionURI") val collectionURI: String
)

data class SeriesDetails(
    @Json(name = "available") val available: Int,
    @Json(name = "collectionURI") val collectionURI: String
)
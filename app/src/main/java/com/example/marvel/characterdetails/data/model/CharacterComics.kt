package com.example.marvel.characterdetails.data.model

import com.squareup.moshi.Json

data class CharacterComics(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val data: ComicsData
)

data class ComicsData(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<ComicsResult>
)

data class ComicsResult(
    @Json(name = "title") val title: String,
    @Json(name = "thumbnail") val thumbnail: ComicsThumbnail
)

data class ComicsThumbnail(
    @Json(name = "path") val path: String,
    @Json(name = "extension") val extension: String
)
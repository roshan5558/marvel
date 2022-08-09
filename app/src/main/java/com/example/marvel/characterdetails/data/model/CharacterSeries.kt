package com.example.marvel.characterdetails.data.model

import com.squareup.moshi.Json

data class CharacterSeries(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val data: SeriesData
)

data class SeriesData(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<SeriesResult>
)

data class SeriesResult(
    @Json(name = "title") val title: String,
    @Json(name = "thumbnail") val thumbnail: SeriesThumbnail
)

data class SeriesThumbnail(
    @Json(name = "path") val path: String,
    @Json(name = "extension") val extension: String
)
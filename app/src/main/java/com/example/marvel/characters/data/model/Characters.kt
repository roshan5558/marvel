package com.example.marvel.characters.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Characters(
    @Json(name = "code") val code: Int,
    @Json(name = "data") val data: Data
)

data class Data(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<Result>
)

@Entity(tableName = "characters_table")
data class Result(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") @Json(name = "id") val id: Int,
    @ColumnInfo(name = "name") @Json(name = "name") val name: String,
    @ColumnInfo(name = "thumbnail") @Json(name = "thumbnail") val thumbnail: Thumbnail,
    @ColumnInfo(name = "bookmark") var bookmark: Boolean
)

data class Thumbnail(
    @ColumnInfo(name = "path") @Json(name = "path") val path: String,
    @ColumnInfo(name = "extension") @Json(name = "extension") val extension: String
)
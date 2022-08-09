package com.example.marvel.core.room.converters

import androidx.room.TypeConverter
import com.example.marvel.characters.data.model.Thumbnail
import com.example.marvel.core.utils.Constants
import org.json.JSONObject

/**
 * ThumbnailTypeConverter class converts the Thumbnail to String and String to Thumbnail
 */
class ThumbnailTypeConverter {
    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return JSONObject().apply {
            put(Constants.KEY_PATH, thumbnail.path)
            put(Constants.KEY_EXTENSION, thumbnail.extension)
        }.toString()
    }

    @TypeConverter
    fun toThumbnail(source: String): Thumbnail {
        val json = JSONObject(source)
        return Thumbnail(
            json.getString(Constants.KEY_PATH),
            json.getString(Constants.KEY_EXTENSION)
        )
    }
}
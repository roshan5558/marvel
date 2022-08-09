package com.example.marvel.characters.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.characters.data.repository.CharactersRepository
import com.example.marvel.characters.ui.main.viewmodel.CharactersViewModel
import com.example.marvel.core.room.MarvelDao

class CharactersViewModelFactory(private val marvelDao: MarvelDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(CharactersRepository(marvelDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
package com.example.marvel.characterdetails.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.characterdetails.data.repository.CharacterDetailsRepository
import com.example.marvel.characterdetails.ui.main.viewmodel.CharacterDetailsViewModel

class CharacterDetailsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            return CharacterDetailsViewModel(CharacterDetailsRepository()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
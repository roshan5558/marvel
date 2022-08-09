package com.example.marvel.characters.ui.main.view

import com.example.marvel.characters.data.model.Result

interface CharactersOnItemClickListener {
    fun onItemClicked(characterId: Int)

    fun updateCharacter(result: Result)
}
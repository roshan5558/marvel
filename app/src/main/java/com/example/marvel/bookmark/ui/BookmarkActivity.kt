package com.example.marvel.bookmark.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.characters.data.model.Result
import com.example.marvel.characters.ui.base.CharactersViewModelFactory
import com.example.marvel.characters.ui.main.view.CharactersOnItemClickListener
import com.example.marvel.characters.ui.main.view.adapter.CharacterAdapter
import com.example.marvel.characters.ui.main.viewmodel.CharactersViewModel
import com.example.marvel.core.room.MarvelDatabase
import com.example.marvel.core.utils.Constants
import com.example.marvel.core.utils.MarginItemDecoration
import com.example.marvel.core.utils.Status
import com.example.marvel.core.utils.Utils

class BookmarkActivity : AppCompatActivity(), CharactersOnItemClickListener {

    private lateinit var rcvCharacters: RecyclerView
    private lateinit var tvNoBookmark: TextView

    private lateinit var mCharactersViewModel: CharactersViewModel
    private lateinit var mCharacterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        initializeViews()
        setupViewModel()
        setupObserver()
        setupRecyclerView()

        Utils.setActivityTitle(supportActionBar, Constants.KEY_BOOKMARKS)
        mCharactersViewModel.fetchCharactersFromDb(true)
    }

    override fun onItemClicked(characterId: Int) {
        Toast.makeText(this, Constants.KEY_COMING_SOON, Toast.LENGTH_SHORT).show()
    }

    override fun updateCharacter(result: Result) {
        Toast.makeText(this, Constants.KEY_COMING_SOON, Toast.LENGTH_SHORT).show()
    }

    private fun initializeViews() {
        rcvCharacters = findViewById(R.id.rcv_characters)
        tvNoBookmark = findViewById(R.id.tv_no_bookmark)
    }

    private fun setupViewModel() {
        val marvelDao = MarvelDatabase.getDatabase(applicationContext).getMarvelDao()
        mCharactersViewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(marvelDao)
        ).get(CharactersViewModel::class.java)
    }

    private fun setupObserver() {
        mCharactersViewModel.getCharactersFromDb().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        if (data.filter { character -> character.bookmark }.isNotEmpty()) {
                            mCharacterAdapter.passDataToAdapter(data.filter { result -> result.bookmark })
                        } else {
                            tvNoBookmark.visibility = View.VISIBLE
                        }
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(
                            this@BookmarkActivity,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rcvCharacters.apply {
            val linearLayoutManager = LinearLayoutManager(this@BookmarkActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            mCharacterAdapter =
                CharacterAdapter(
                    this@BookmarkActivity,
                    false,
                    this@BookmarkActivity,
                )
            adapter = mCharacterAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.size_8dp),
                    Constants.KEY_VERTICAL
                )
            )
        }
    }
}
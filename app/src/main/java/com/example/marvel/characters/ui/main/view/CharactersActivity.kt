package com.example.marvel.characters.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.marvel.R
import com.example.marvel.bookmark.ui.BookmarkActivity
import com.example.marvel.characterdetails.ui.main.view.CharacterDetailsActivity
import com.example.marvel.characters.data.model.Result
import com.example.marvel.characters.ui.base.CharactersViewModelFactory
import com.example.marvel.characters.ui.main.view.adapter.CharacterAdapter
import com.example.marvel.characters.ui.main.viewmodel.CharactersViewModel
import com.example.marvel.core.room.MarvelDatabase
import com.example.marvel.core.utils.Constants
import com.example.marvel.core.utils.MarginItemDecoration
import com.example.marvel.core.utils.Status
import com.example.marvel.core.utils.Utils

class CharactersActivity : AppCompatActivity(), CharactersOnItemClickListener {

    private lateinit var rcvCharacters: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var pbLoading: ProgressBar

    private lateinit var mCharactersViewModel: CharactersViewModel
    private lateinit var mCharacterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        initializeViews()
        setupViewModel()
        setupObserver()
        setupRecyclerView()
        setupSwipeRefresh()

        Utils.setActivityTitle(supportActionBar, Constants.KEY_CHARACTERS)
        mCharactersViewModel.fetchCharactersFromDb(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_bookmark) {
            startActivity(Intent(this, BookmarkActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(characterId: Int) {
        launchCharacterDetailsActivity(characterId)
    }

    override fun updateCharacter(result: Result) {
        mCharactersViewModel.bookmarkCharacter(result)
    }

    private fun initializeViews() {
        rcvCharacters = findViewById(R.id.rcv_characters)
        swipeRefresh = findViewById(R.id.swipeRefreshLayout)
        pbLoading = findViewById(R.id.pb_loading)
    }

    private fun setupViewModel() {
        val marvelDao = MarvelDatabase.getDatabase(applicationContext).getMarvelDao()
        mCharactersViewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(marvelDao)
        ).get(CharactersViewModel::class.java)
    }

    private fun setupObserver() {
        mCharactersViewModel.getCharactersFromDb().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        mCharacterAdapter.passDataToAdapter(data)
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(this@CharactersActivity, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        mCharactersViewModel.getLoading().observe(this) {
            if (it) {
                pbLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.GONE
            }
        }

        mCharactersViewModel.getMessage().observe(this) {
            Toast.makeText(this@CharactersActivity, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        rcvCharacters.apply {
            val linearLayoutManager = LinearLayoutManager(this@CharactersActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            mCharacterAdapter =
                CharacterAdapter(
                    this@CharactersActivity,
                    false,
                    this@CharactersActivity,
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

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            mCharactersViewModel.fetchCharactersFromDb(false)
        }
    }

    private fun launchCharacterDetailsActivity(characterId: Int) {
        Intent(this, CharacterDetailsActivity::class.java)
            .putExtra(Constants.KEY_CHARACTER_ID, characterId)
            .let {
                startActivity(it)
            }
    }
}

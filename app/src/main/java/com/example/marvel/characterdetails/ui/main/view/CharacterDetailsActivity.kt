package com.example.marvel.characterdetails.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.bookmark.ui.BookmarkActivity
import com.example.marvel.characterdetails.ui.base.CharacterDetailsViewModelFactory
import com.example.marvel.characterdetails.ui.main.view.adapter.ComicsAdapter
import com.example.marvel.characterdetails.ui.main.view.adapter.SeriesAdapter
import com.example.marvel.characterdetails.ui.main.viewmodel.CharacterDetailsViewModel
import com.example.marvel.core.utils.Constants
import com.example.marvel.core.utils.MarginItemDecoration
import com.example.marvel.core.utils.Status
import com.example.marvel.core.utils.Utils

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var ivThumbnail: ImageView
    private lateinit var tvRecommendedSeries: TextView
    private lateinit var tvRecommendedSeriesSeeALl: TextView
    private lateinit var tvRecommendedComics: TextView
    private lateinit var tvRecommendedComicsSeeALl: TextView
    private lateinit var rcvComics: RecyclerView
    private lateinit var rcvSeries: RecyclerView
    private lateinit var pbLoading: ProgressBar

    private lateinit var mCharacterDetailsViewModel: CharacterDetailsViewModel
    private lateinit var mSeriesAdapter: SeriesAdapter
    private lateinit var mComicsAdapter: ComicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        initializeViews()
        setupViewModel()
        setupObserver()
        setupSeriesRecyclerView()
        setupComicsRecyclerView()

        mCharacterDetailsViewModel.fetchCharacterDetails(getCharacterId())
        mCharacterDetailsViewModel.fetchCharacterComics(getCharacterId())
        mCharacterDetailsViewModel.fetchCharacterSeries(getCharacterId())
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

    private fun initializeViews() {
        ivThumbnail = findViewById(R.id.iv_thumbnail)
        tvRecommendedSeries = findViewById(R.id.tv_recommended_series)
        tvRecommendedSeriesSeeALl = findViewById(R.id.tv_recommended_series_see_all)
        tvRecommendedComics = findViewById(R.id.tv_recommended_comics)
        tvRecommendedComicsSeeALl = findViewById(R.id.tv_recommended_comics_see_all)
        rcvComics = findViewById(R.id.rcv_comics)
        rcvSeries = findViewById(R.id.rcv_series)
        pbLoading = findViewById(R.id.pb_loading)
    }

    private fun setupViewModel() {
        mCharacterDetailsViewModel = ViewModelProvider(
            this,
            CharacterDetailsViewModelFactory()
        ).get(CharacterDetailsViewModel::class.java)
    }

    private fun setupObserver() {
        mCharacterDetailsViewModel.getCharacterDetails().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { characterDetails ->
                        Utils.setActivityTitle(
                            supportActionBar,
                            characterDetails.data.results[0].name
                        )
                        val thumbnailUrl =
                            characterDetails.data.results[0].thumbnail.path + "." + characterDetails.data.results[0].thumbnail.extension
                        Glide.with(this@CharacterDetailsActivity)
                            .load(thumbnailUrl.replace(Constants.KEY_HTTP, Constants.KEY_HTTPS))
                            .error(R.drawable.image_broken_variant)
                            .into(ivThumbnail)
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(
                            this@CharacterDetailsActivity,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        mCharacterDetailsViewModel.getCharacterComics().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { comicsList ->
                        if (comicsList.data.results.isNotEmpty()) {
                            tvRecommendedSeries.visibility = View.VISIBLE
                            tvRecommendedSeriesSeeALl.visibility = View.VISIBLE

                            mComicsAdapter.passDataToAdapter(comicsList.data.results)
                        }
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(
                            this@CharacterDetailsActivity,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        mCharacterDetailsViewModel.getCharacterSeries().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { seriesList ->
                        if (seriesList.data.results.isNotEmpty()) {
                            tvRecommendedComics.visibility = View.VISIBLE
                            tvRecommendedComicsSeeALl.visibility = View.VISIBLE

                            mSeriesAdapter.passDataToAdapter(seriesList.data.results)
                        }
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(
                            this@CharacterDetailsActivity,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        mCharacterDetailsViewModel.getLoading().observe(this) {
            if (it) {
                pbLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.GONE
            }
        }
    }

    private fun setupSeriesRecyclerView() {
        rcvSeries.apply {
            val linearLayoutManager = LinearLayoutManager(this@CharacterDetailsActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            mSeriesAdapter = SeriesAdapter()
            adapter = mSeriesAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.size_8dp),
                    Constants.KEY_HORIZONTAL
                )
            )
        }
    }

    private fun setupComicsRecyclerView() {
        rcvComics.apply {
            val linearLayoutManager = LinearLayoutManager(this@CharacterDetailsActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            mComicsAdapter = ComicsAdapter()
            adapter = mComicsAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.size_8dp),
                    Constants.KEY_HORIZONTAL
                )
            )
        }
    }

    private fun getCharacterId(): Int {
        return intent.getIntExtra(Constants.KEY_CHARACTER_ID, 0)
    }
}
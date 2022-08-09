package com.example.marvel.characters.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.characters.data.model.Result
import com.example.marvel.characters.data.repository.CharactersRepository
import com.example.marvel.core.utils.Constants
import com.example.marvel.core.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    private val mCharactersFromDb = MutableLiveData<Resource<List<Result>>>()
    private val mCompositeDisposable = CompositeDisposable()
    private val mShowLoading = MutableLiveData<Boolean>()
    private val mShowMessage = MutableLiveData<String>()

    /**
     * fetchCharacters function is used to fetch all the Characters from server
     * and store it in local database
     */
    private fun fetchCharacters() {
        mCompositeDisposable.add(
            charactersRepository.getAllCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.data.results.isNotEmpty()) {
                        insertCharactersToDb(it.data.results)
                    }
                }, {
                    mShowMessage.postValue(Constants.KEY_SOMETHING_WENT_WRONG)
                })
        )
    }

    /**
     * fetchCharacters function is used to fetch all the Characters from the local database
     * and show it to user and in the meanwhile also fetches the updated Characters from server
     */
    fun fetchCharactersFromDb(isBookmarkActivity: Boolean) {
        mShowLoading.postValue(true)
        mCompositeDisposable.add(
            charactersRepository.allCharactersFromDb
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        mShowLoading.postValue(false)
                        mCharactersFromDb.postValue(Resource.success(it))
                    }
                    if (!isBookmarkActivity) {
                        fetchCharacters()
                    }
                }, {
                    mCharactersFromDb.postValue(
                        Resource.error(Constants.KEY_SOMETHING_WENT_WRONG, null)
                    )
                })
        )
    }

    /**
     * insertCharactersToDb function is used tostores the latest fetched Characters
     * from serve to local database
     */
    private fun insertCharactersToDb(results: List<Result>) {
        mCompositeDisposable.add(
            charactersRepository.insertAllCharacters(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val digit: Long = -1
                    if (Collections.frequency(it, digit) != it.size) {
                        fetchCharactersFromDb(false)
                    }
                }, {
                    mShowMessage.postValue(Constants.KEY_SOMETHING_WENT_WRONG)
                })
        )
    }

    /**
     * bookmarkCharacter function is used to bookmarks the particular character in local database
     */
    fun bookmarkCharacter(result: Result) {
        mCompositeDisposable.add(
            charactersRepository.updateCharacters(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (result.bookmark) {
                        mShowMessage.postValue(Constants.KEY_ADDED_TO_BOOKMARK)
                    } else {
                        mShowMessage.postValue(Constants.KEY_REMOVED_FROM_BOOKMARK)
                    }
                }, {
                    mShowMessage.postValue(Constants.KEY_SOMETHING_WENT_WRONG)
                })
        )
    }

    fun getCharactersFromDb(): LiveData<Resource<List<Result>>> = mCharactersFromDb

    fun getLoading(): LiveData<Boolean> = mShowLoading

    fun getMessage(): LiveData<String> = mShowMessage

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }
}
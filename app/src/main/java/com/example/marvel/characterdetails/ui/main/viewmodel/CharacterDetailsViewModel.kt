package com.example.marvel.characterdetails.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.characterdetails.data.model.CharacterComics
import com.example.marvel.characterdetails.data.model.CharacterDetails
import com.example.marvel.characterdetails.data.model.CharacterSeries
import com.example.marvel.characterdetails.data.repository.CharacterDetailsRepository
import com.example.marvel.core.utils.Constants
import com.example.marvel.core.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterDetailsViewModel(private val characterDetailsRepository: CharacterDetailsRepository) :
    ViewModel() {

    private val mCharacterDetails = MutableLiveData<Resource<CharacterDetails>>()
    private val mCharacterComics = MutableLiveData<Resource<CharacterComics>>()
    private val mCharacterSeries = MutableLiveData<Resource<CharacterSeries>>()
    private val mCompositeDisposable = CompositeDisposable()
    private val mShowLoading = MutableLiveData<Boolean>()

    /**
     * fetchCharacterDetails function is used to fetch the Character details from the server
     * and show it to user
     */
    fun fetchCharacterDetails(characterId: Int) {
        mShowLoading.postValue(true)
        mCompositeDisposable.add(
            characterDetailsRepository.getCharacterDetails(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mShowLoading.postValue(false)
                    mCharacterDetails.postValue(Resource.success(it))
                }, { throwable ->
                    mCharacterDetails.postValue(
                        Resource.error(
                            Constants.KEY_SOMETHING_WENT_WRONG + throwable.message,
                            null
                        )
                    )
                })
        )
    }

    /**
     * fetchCharacterComics function is used to fetch the Character comics from the server
     * and show it to user
     */
    fun fetchCharacterComics(characterId: Int) {
        mCompositeDisposable.add(
            characterDetailsRepository.getCharacterComics(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mCharacterComics.postValue(Resource.success(it))
                }, { throwable ->
                    mCharacterComics.postValue(
                        Resource.error(
                            Constants.KEY_SOMETHING_WENT_WRONG + throwable.message,
                            null
                        )
                    )
                })
        )
    }

    /**
     * fetchCharacterSeries function is used to fetch the Character series from the server
     * and show it to user
     */
    fun fetchCharacterSeries(characterId: Int) {
        mCompositeDisposable.add(
            characterDetailsRepository.getCharacterSeries(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mCharacterSeries.postValue(Resource.success(it))
                }, { throwable ->
                    mCharacterSeries.postValue(
                        Resource.error(
                            Constants.KEY_SOMETHING_WENT_WRONG + throwable.message,
                            null
                        )
                    )
                })
        )
    }

    fun getCharacterComics(): LiveData<Resource<CharacterComics>> = mCharacterComics

    fun getCharacterDetails(): LiveData<Resource<CharacterDetails>> = mCharacterDetails

    fun getCharacterSeries(): LiveData<Resource<CharacterSeries>> = mCharacterSeries

    fun getLoading(): LiveData<Boolean> = mShowLoading

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }
}
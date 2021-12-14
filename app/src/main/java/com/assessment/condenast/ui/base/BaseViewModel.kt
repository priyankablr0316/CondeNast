package com.assessment.condenast.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

/**
 * Common view model to keep common used livedata and others.
 * Progress bar and error needs to be displayed on all the screens which should be common.
 */
open class BaseViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    protected val mutableProgressIndicator = MutableLiveData(false)
    val progressIndicator: LiveData<Boolean> get() = mutableProgressIndicator

    protected val errorMutableLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableLiveData

}
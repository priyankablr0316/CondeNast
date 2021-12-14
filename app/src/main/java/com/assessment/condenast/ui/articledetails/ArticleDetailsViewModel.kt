package com.assessment.condenast.ui.articledetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assessment.condenast.data.AppDataManager
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse
import com.assessment.condenast.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is used to fetch likes and comments from remote and show it on the screen.
 */
class ArticleDetailsViewModel @Inject constructor(
    application: Application,
    private val appDataManager: AppDataManager
) : BaseViewModel(application) {

    private val likeMutableLiveData = MutableLiveData<Int>()
    val likeLiveData: LiveData<Int> get() = likeMutableLiveData

    private val commentsMutableLiveData = MutableLiveData<Int>()
    val commentsLiveData: LiveData<Int> get() = commentsMutableLiveData

    fun fetchLikesForArticles(articleId: String) {
        viewModelScope.launch {
            mutableProgressIndicator.value = true
            when (val result = appDataManager.getNewInfoApiRepository().getLikesForArticle(articleId)) {
                is Result.Success<LikesResponse> -> {
                    result.data.let {
                        likeMutableLiveData.value = it.likes
                    }
                    mutableProgressIndicator.value = false
                }
                is Result.Error -> {
                    mutableProgressIndicator.value = false
                    errorMutableLiveData.value = result.message
                }
            }
        }
    }

    fun fetchCommentsForArticles(articleId: String) {
        viewModelScope.launch {
            mutableProgressIndicator.value = true
            when (val result = appDataManager.getNewInfoApiRepository().getCommentsForArticle(articleId)) {
                is Result.Success<CommentsResponse> -> {
                    result.data.let {
                        commentsMutableLiveData.value = it.comments
                    }
                    mutableProgressIndicator.value = false
                }
                is Result.Error -> {
                    mutableProgressIndicator.value = false
                    errorMutableLiveData.value = result.message
                }
            }
        }
    }

}
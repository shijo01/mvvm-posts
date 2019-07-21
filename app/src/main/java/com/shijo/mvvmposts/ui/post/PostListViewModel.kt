package com.shijo.mvvmposts.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.shijo.mvvmposts.R
import com.shijo.mvvmposts.base.BaseViewModel
import com.shijo.mvvmposts.model.Post
import com.shijo.mvvmposts.model.db.PostDao
import com.shijo.mvvmposts.model.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    val postListAdapter = PostListAdapter()
    private lateinit var subscription: Disposable
    private val progressBarVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun getProgressBarVisibility(): MutableLiveData<Int> {
        return progressBarVisibility
    }


    private fun loadPosts() {
        subscription = Observable
            .fromCallable { postDao.all }
            .concatMap { dbPostList: List<Post> ->
                if (dbPostList.isNullOrEmpty())
                    postApi.getPosts().concatMap { apiPostList ->
                        postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe({ posts -> onRetrievePostListSuccess(posts) }, { onRetrievePostListError() })
    }

    private fun onRetrievePostListStart() {
        progressBarVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrievePostListFinish() {
        progressBarVisibility.value = View.INVISIBLE

    }

    private fun onRetrievePostListSuccess(posts: List<Post>) {
        postListAdapter.updatePostList(posts)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error

    }
}

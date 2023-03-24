package com.smtz.note.presenters

import androidx.lifecycle.ViewModel
import com.smtz.note.views.BaseView

abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel() {

    protected lateinit var mView : T

    override fun initPresenter(view: T){
        mView = view
    }
}
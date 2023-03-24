package com.smtz.note.presenters

import androidx.lifecycle.LifecycleOwner
import com.smtz.note.views.BaseView

interface BasePresenter<V: BaseView> {
    fun onUiReady(owner: LifecycleOwner)
    fun initPresenter(view: V)
}
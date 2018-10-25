package com.dbeqiraj.pointofsale.base

import javax.inject.Inject

open class BasePresenter<V: BaseView> {
    @Inject
    protected lateinit var mView: V

}
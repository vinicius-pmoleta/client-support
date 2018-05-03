package com.clientsupport.core.structure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<Presenter : BaseContract.Action> : AppCompatActivity() {

    @Inject
    lateinit var presenter: Presenter

    abstract fun injectDependencies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }
}
package br.com.clean.core.view.ui

import br.com.clean.core.gateway.mvvm.Controller

abstract class MvvmActivity<C: Controller>: BaseActivity() {
    protected val controller by lazy { setupController() }

    protected abstract fun setupController(): C
}
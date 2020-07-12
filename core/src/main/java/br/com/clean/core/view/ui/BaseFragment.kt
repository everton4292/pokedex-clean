package br.com.clean.core.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.clean.core.business.dto.ValueOutput
import br.com.clean.core.business.exception.AuthenticationException
import br.com.clean.core.business.exception.HttpException
import br.com.clean.core.business.exception.InternetConnectionException
import br.com.clean.core.gateway.mvvm.Controller

abstract class BaseFragment<C : Controller> : Fragment() {
    protected var hideToolbar = false

    protected val controller by lazy { setupController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        if (hideToolbar) (activity as AppCompatActivity).supportActionBar?.hide()
        observe(channelName(), Observer { handleResponse(it as ValueOutput<*>) })
    }

    protected abstract fun setupViews(view: View)

    protected abstract fun channelName(): String

    protected abstract fun setupController(): C

    protected abstract fun getLayout(): Int

    protected open fun handleResponse(state: ValueOutput<*>) {
        if (state.isError()) {
            handleThrowable(state.error)
        } else {
            handleSuccess(state.value)
        }
    }

    private fun handleThrowable(error: Throwable?) {
        when (error) {
            is AuthenticationException -> handleAuthError()
            is HttpException -> handleHttpError(error)
            is InternetConnectionException -> handleConnectionError()
            else -> handleError(error)
        }
    }

    protected open fun handleAuthError() {}

    protected open fun handleHttpError(error: HttpException) {}

    protected open fun handleConnectionError() {}

    protected open fun handleError(error: Throwable?) {}

    protected open fun handleSuccess(value: Any?) {}

    protected fun observe(channelName: String, listener: Observer<Any>) {
        controller.observe(channelName, this, listener)
    }

    protected fun setupToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = false) {
        (activity as? BaseActivity)?.resetToolbar(toolbar, homeAsUpEnabled)
    }
}
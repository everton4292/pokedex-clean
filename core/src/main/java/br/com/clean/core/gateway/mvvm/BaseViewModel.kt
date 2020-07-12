package br.com.clean.core.gateway.mvvm

/**
 *
 * This file has the main components of the Gateway (aka Interface Adapter) Layer
 *
 * The Gateway Layer is the bridge between the View (Android heavily-dependent) Layer and
 * the Business Layer (which must have no Android dependency at all).
 * Android dependency should be kept at a minimum in this Layer.
 *
 * The BaseViewModel is the ViewModel that all others inherit from.
 * BaseViewModel's subclasses must declare named channels to which responses for
 * the View Layer's calls will be posted.
 * The View Layer may observe those channels. If the given channel name is valid
 * (i.e. it had been declared by the ViewModel), then a corresponding LiveData is created.
 *
 * The ViewState represents a event/state for the View Layer that can be flagged as handled.
 */

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.clean.core.business.dto.Output
import br.com.clean.core.business.interactor.CallbackDecorator
import br.com.clean.core.business.interactor.CompositeJobDisposable
import br.com.clean.core.business.interactor.UseCase
import br.com.clean.core.business.interactor.UseCaseDispatcher
import kotlinx.coroutines.Job


abstract class BaseViewModel: ViewModel(), Controller {
    private val channels: MutableMap<String, MutableLiveData<Any>> = mutableMapOf()
    private val compositeJobDisposable = CompositeJobDisposable()

    final override fun observe(channelName: String, owner: LifecycleOwner, listener: Observer<Any>) {
        if(channels[channelName] == null) channels[channelName] = MutableLiveData()
        channels[channelName]!!.observe(owner, listener)
    }

    fun disposeAll() {
        compositeJobDisposable.cancel()
    }

    protected open fun postValue(channelName: String, value: Any) {
        val channel = channels[channelName]
        channel?.postValue(value)
    }

    protected open fun <P,R> dispatchUseCase(param: P?, useCase: UseCase<P,R>, listener: (Output<R>)->Unit): Job? {
        val dispatcher = UseCaseDispatcher(CallbackDecorator(useCase, listener))
        val job = dispatcher.dispatch(param)
        compositeJobDisposable.add(job)
        return job
    }
}
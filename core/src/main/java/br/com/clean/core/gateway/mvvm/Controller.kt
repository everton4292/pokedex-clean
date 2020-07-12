package br.com.clean.core.gateway.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Controller {
    fun observe(channelName: String, owner: LifecycleOwner, listener: Observer<Any>)
}
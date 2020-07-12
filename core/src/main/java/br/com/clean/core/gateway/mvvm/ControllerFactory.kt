package br.com.clean.core.gateway.mvvm

interface ControllerFactory<V,T: Controller> {
    fun create(context: V): T
}
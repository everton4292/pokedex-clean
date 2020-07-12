package com.example.pokedex

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import br.com.clean.core.business.dto.ValueOutput
import br.com.clean.core.gateway.mvvm.BaseViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<T : BaseViewModel> {
    protected var viewModel: T? = null
    protected lateinit var observer: TestObserver

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    open fun setup() {
        setupViewModel()
        observeViewModel()
    }

    protected abstract fun setupViewModel()

    protected fun assertViewStateError(exception: Exception) {
        observer.viewState?.let {
            Assert.assertTrue(it.isError())
            Assert.assertEquals(it.error, exception)
        }
    }

    protected fun assertViewStateSuccess(value: Any) {
        observer.viewState?.let {
            Assert.assertTrue(it.isSuccess())
            Assert.assertEquals(it.value, value)
        }
    }

    open fun teardown() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    private fun observeViewModel() {
        observer = TestObserver()
        viewModel?.let {
            for (channel in it.getChannels()) {
                it.observe(channel, getLifecycleOwner(), observer)
            }
        }
    }

    private fun getLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner: LifecycleOwner = mock()
        val lifecycle = LifecycleRegistry(lifecycleOwner).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        whenever(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        return lifecycleOwner
    }
}

open class TestObserver : Observer<Any> {
    var viewState: ValueOutput<*>? = null

    override fun onChanged(value: Any?) {
        viewState = value as ValueOutput<*>
    }
}
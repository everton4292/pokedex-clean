package br.com.clean.core.view.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import br.com.clean.core.gateway.mvvm.Controller
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<C : Controller> :
    BottomSheetDialogFragment() {
    protected val controller by lazy { setupController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
    }

    protected abstract fun setupViews(view: View)

    protected abstract fun setupController(): C

    protected abstract fun getBottomSheetFrameLayoutId(): Int

    protected fun observe(channelName: String, listener: Observer<Any>) {
        controller.observe(channelName, this, listener)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog.window == null) return dialog
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnShowListener { expandSheet(dialog as BottomSheetDialog) }

        return dialog
    }

    private fun expandSheet(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<FrameLayout>(getBottomSheetFrameLayoutId()) ?: return
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = bottomSheet.height
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
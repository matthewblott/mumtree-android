package com.example.mumtree

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import dev.hotwire.turbo.nav.TurboNavGraphDestination
@TurboNavGraphDestination(uri = "mumtree://fragment/web/modal")
class ModalWebFragment : WebFragment() {
  override fun onViewCreated(
    view: View, savedInstanceState: Bundle? ){
    super.onViewCreated(view, savedInstanceState)
    configureCloseIcon()
  }
  private fun configureCloseIcon() {
    toolbarForNavigation()?.navigationIcon =
      ContextCompat.getDrawable( requireContext(), R.drawable.ic_close
      )
  }
}

package com.example.mumtree

import dev.hotwire.turbo.fragments.TurboWebFragment
import dev.hotwire.turbo.nav.TurboNavGraphDestination
import android.os.Bundle 
import android.view.View

@TurboNavGraphDestination(uri = "mumtree://fragment/web")
open class WebFragment : 
  TurboWebFragment(), NavDestination {

  override fun onViewCreated(
    view: View, savedInstanceState: Bundle?
  ){
    super.onViewCreated(view, savedInstanceState)
    configureMenuClickListener() 
  }
    
  override fun onVisitErrorReceived( 
    location: String, errorCode: Int
  ){
    when (errorCode) {
      401 -> if(isActive) navigate(Api.loginUrl)
      else -> super.onVisitErrorReceived(location, errorCode) }
  }

  override fun onColdBootPageCompleted(location: String) { 
    super.onColdBootPageCompleted(location)
    session.webView.attachWebBridge(requireContext())
  }
  private fun configureMenuClickListener() {
    toolbarForNavigation()?.setOnMenuItemClickListener {
    session.webView.click(it.itemId)
    return@setOnMenuItemClickListener true }
  }
  override fun onVisitStarted(location: String) { 
    super.onVisitStarted(location) 
    toolbarForNavigation()?.menu?.clear()
  } 
}

package com.example.mumtree

import dev.hotwire.turbo.fragments.TurboWebFragment
import dev.hotwire.turbo.nav.TurboNavGraphDestination

@TurboNavGraphDestination(uri = "mumtree://fragment/web")
open class WebFragment : TurboWebFragment(), NavDestination {
  override fun onVisitErrorReceived( location: String, errorCode: Int
  ){
    when (errorCode) {
      401 -> if(isActive) navigate(Api.loginUrl)
      else -> super.onVisitErrorReceived(location, errorCode) }
  }
}

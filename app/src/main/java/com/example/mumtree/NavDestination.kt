package com.example.mumtree

import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import dev.hotwire.turbo.config.TurboPathConfigurationProperties import dev.hotwire.turbo.config.context
import dev.hotwire.turbo.nav.TurboNavDestination
import dev.hotwire.turbo.nav.TurboNavPresentationContext
import java.net.URL

interface NavDestination: TurboNavDestination {

  val tabsViewModel: TabsViewModel get() =
    (sessionNavHostFragment as SessionNavHostFragment)
      .tabsViewModel
  
  override fun shouldNavigateTo(newLocation: String): Boolean {
    return when {
      isTabUrl(newLocation)->{
        switchToTabForUrl(newLocation)        
        false 
      } 
      isExternal(newLocation) -> {
        //TODO: open in browser false
        false
      }
      isPathDirective(newLocation) -> {
        executePathDirective(newLocation)
        false
      }
      else -> true
    }
  }

  private fun executePathDirective(url: String) {
    val url = URL(url)
    when (url.path) {
      "/recede_historical_location" -> navigateUp()
      "/refresh_historical_location" -> refresh() }
  }
  private fun isPathDirective(url: String): Boolean { return url.contains("_historical_location")
  }
  private fun isExternal(location: String): Boolean { return !location.startsWith(Api.rootUrl)
  }
  fun switchToTabForUrl(url: String) { 
    val mainActivity =
    sessionNavHostFragment.activity as MainActivity
    val tabId = tabsViewModel.tabForUrl(url)?.id ?: R.id.tab_home
    when(pathProperties.context) { TurboNavPresentationContext.MODAL -> navigateUp() else -> {}
    }
    mainActivity.tabBar.selectedItemId = tabId
  }
  private fun isTabUrl(url: String): Boolean { return tabsViewModel.tabForUrl(url) != null
  }
  override fun getNavigationOptions(
    newLocation: String,
    newPathProperties: TurboPathConfigurationProperties
  ): NavOptions {
    return when (newPathProperties.context) {
      TurboNavPresentationContext.MODAL -> slideAnimation()
      else ->
        super.getNavigationOptions(newLocation, newPathProperties)
    }
  }
  private fun slideAnimation(): NavOptions {
    return navOptions {
      anim {
        enter = R.anim.nav_slide_enter
        exit = R.anim.nav_slide_exit
        popEnter = R.anim.nav_slide_pop_enter
        popExit = R.anim.nav_slide_pop_exit
      }
    }
  }

  fun dismissLoginScreen() {
    if (this.location == Api.loginUrl) {
      navigateUp() }
  }

}

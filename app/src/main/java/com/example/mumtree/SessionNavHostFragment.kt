package com.example.mumtree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.hotwire.turbo.config.TurboPathConfiguration
import dev.hotwire.turbo.session.TurboSessionNavHostFragment
import kotlin.reflect.KClass
import androidx.fragment.app.activityViewModels

class SessionNavHostFragment : TurboSessionNavHostFragment() {
  override var sessionName = ""
  override var startLocation = Api.rootUrl

  val tabsViewModel: TabsViewModel by activityViewModels()

  override val registeredActivities: List<KClass<out AppCompatActivity>>
    get() = listOf()

  override val registeredFragments: List<KClass<out Fragment>>
    get() = listOf(
      WebFragment::class,
      ModalWebFragment::class,
      TabbedWebFragment::class
    )

  override fun onCreate(savedInstanceState: Bundle?) {
    sessionName = "tab_$tag"
    tabsViewModel.tabForId(id)?.url?.let {
      startLocation = it
    }

    super.onCreate(savedInstanceState)
  }

  override fun onSessionCreated() {
    super.onSessionCreated()
    session.webView.settings.userAgentString = "Mumtree Turbo Native Android"

    session.webView.addJavascriptInterface( 
      WebBridgeMessageHandler(this), "messageHandler"
    ) 
  }

  override val pathConfigurationLocation: TurboPathConfiguration.Location
    get() = TurboPathConfiguration.Location(assetFilePath = "json/configuration.json")
}

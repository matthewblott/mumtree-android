package com.example.mumtree

import dev.hotwire.turbo.nav.TurboNavGraphDestination

@TurboNavGraphDestination(uri = "mumtree://fragment/web/tab")
class TabbedWebFragment : WebFragment()

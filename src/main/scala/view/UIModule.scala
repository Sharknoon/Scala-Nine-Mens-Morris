package view

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import view.tui.TUI

class UIModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[UI].to[TUI]
  }
}

package view.gui

import view.UI

class GUI extends UI {
  override def start(): Unit = {
    GUIFX.main(Array.empty)
  }
}

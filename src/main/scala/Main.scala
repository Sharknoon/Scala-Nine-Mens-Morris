import view.gui.GUI
import view.tui.TUI

object Main {
  def main(args: Array[String]): Unit = {
    startTUI()
    //startGUI()
  }

  def startGUI(): Unit = {
    GUI.main(Array.empty)
  }

  def startTUI(): Unit = {
    new TUI()
  }
}

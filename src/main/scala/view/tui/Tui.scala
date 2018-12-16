package view.tui

import controller.MenuController

class Tui {

  getStartMenuInput()

  def getStartMenuInput(): Unit = {

    println("Bitte Spielernamen eingeben, um ein neues Spiel zu starten. Bedenke dass der Spieler 1 immer die Farbe weis erhält und damit beginnt.")
    println("Spieler 1:")
    val player1 = scala.io.StdIn.readLine()
    println("Spieler 2:")
    val player2 = scala.io.StdIn.readLine()

    new MenuController((player1, player2))
  }


}

package controller

import model._


class StartMenu(playerNames: (String, String)) {

  startNewGame()

  def startNewGame(): Game = {
    val player1 = Player(name = playerNames._1, color = Color.WHITE)
    val player2 = Player(name = playerNames._2, color = Color.BLACK)

    val playground = Playground()

    Game((player1, player2), playground)
  }

}

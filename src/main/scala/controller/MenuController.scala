package controller

import model._


class MenuController(playerNames: (String, String)) {

  def startNewGame(): GameController = {
    val player1 = Player(name = playerNames._1, color = Color.WHITE)
    val player2 = Player(name = playerNames._2, color = Color.BLACK)

    val playground = Playground()
    val game = Game((player1, player2), playground)

    new GameController(game)
  }
}

package controller

import model._


class MenuController(playerNames: (String, String)) {

  /*
   Create new game with the two player names and return the game controller
   */
  def startNewGame(): GameController = {
    val player1 = Player(playerNames._1, Color.WHITE)
    val player2 = Player(playerNames._2, Color.BLACK)

    val playground = Playground()
    val game = Game((player1, player2), playground)

    new GameController(game)
  }
}

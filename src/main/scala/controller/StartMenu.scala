package controller

import model._


class StartMenu {

  def startNewGame(players : (String, String)) : Playground = {

    val player1 = Player(players._1, List[Token](), Color.WHITE)
    val player2 = Player(players._2, List[Token](), Color.BLACK)

    createTokens(player1)
    createTokens(player2)

    createPlayground()
  }

  def createTokens(player : Player): Unit = player.tokens = List.fill(GameConstants.AMOUNT_TOKENS)(Token(player))
  def createPlayground() = Playground(List())
}

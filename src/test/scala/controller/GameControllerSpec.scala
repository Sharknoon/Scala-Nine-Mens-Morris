package controller

import model.{Color, Game, Player, Playground}
import org.scalatest.WordSpec

class GameControllerSpec extends WordSpec {

  "A controller" when {
    val player1Name = "Bob"
    val player2Name = "Alice"
    val player1 = Player(player1Name, Color.WHITE)
    val player2 = Player(player2Name, Color.BLACK)
    val playground = Playground()
    val game = Game((player1, player2), playground)
    "created" should {
      val gameController = new GameController(game)
      "return the game itself" in {
        assert(gameController.getGame == game)
      }
      "start with the first player as active Player" in {
        assert(gameController.getActivePlayer == player1)
      }
      "allow the players to set tokens" in {
        assert(gameController.canSetTokens)
      }
      "have a empty field" in {
        playground.fields.keys.foreach(
          position =>
            assert(gameController.isPositionFree(position))
        )
      }
    }
  }

}

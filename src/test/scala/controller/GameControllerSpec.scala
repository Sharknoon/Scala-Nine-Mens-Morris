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
      "have a empty playground" in {
        playground.fields.keys.foreach(
          position =>
            assert(gameController.isPositionFree(position))
        )
      }
      "have the ability to change the player" in {
        gameController.changePlayer()
        assert(gameController.getActivePlayer == player2)
      }
      "allow every player to set tokens" in {
        assert(gameController.canSetTokens)
        gameController.changePlayer()
        assert(gameController.canSetTokens)
      }
      "allow a player to set 9 tokens at the start of the game" in {
        for (i <- 1 to 9) {
          assert(gameController.canSetTokens)
          gameController.setToken(((i % 3) + 1, (i % 8) + 1))
          println(((i % 3) + 1, (i % 8) + 1))
        }
        assert(!gameController.canSetTokens)
        assert(!gameController.isGameOver)
      }
      "have the ability to delete a token in case of a muehle" in {
        assert(!gameController.isPositionFree((2, 2)))
        gameController.changePlayer()
        gameController.deleteOpponentToken((2, 2))
        gameController.changePlayer()
        assert(gameController.isPositionFree((2, 2)))
        gameController.setToken((2, 2))
      }
      "have the ability to move tokens once the tokens used to set are used up" in {
        assert(!gameController.canSetTokens)
        assert(!gameController.canJumpTokens)
        assert(gameController.canMove((2, 2)))
        assert(!gameController.moveToken((2, 2), (2, 4)))
        assert(gameController.moveToken((2, 2), (2, 3)))
        assert(gameController.moveToken((2, 3), (2, 2)))
      }
      "have the ability to jump tokens once the tokens are all removed but 3" in {
        assert(!gameController.canJumpTokens)
        gameController.changePlayer()
        for (i <- 4 to 9) {
          gameController.deleteOpponentToken(((i % 3) + 1, (i % 8) + 1))
        }
        gameController.changePlayer()
        assert(gameController.canJumpTokens)
      }

    }
  }

}

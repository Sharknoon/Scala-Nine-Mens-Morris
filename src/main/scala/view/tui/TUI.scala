package view.tui

import controller.{GameController, MenuController}
import model.{Color, GameConstants, Playground, StringConstants}
import view.UI

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn
import scala.util.Try
import scala.util.control.Breaks._

class TUI extends UI {

  def setStartMenuInput(): Unit = {
    println(StringConstants.ASK_FOR_PLAYER_NAMES)
    println(StringConstants.PLAYER1)
    val player1 = scala.io.StdIn.readLine()
    println(StringConstants.PLAYER2)
    val player2 = scala.io.StdIn.readLine()

    // Empty names
    if (player1.isEmpty || player2.isEmpty) {
      println(StringConstants.EMPTY_PLAYER_NAMES)
      setStartMenuInput()
      return
    }

    // Start new game
    val menuController = new MenuController((player1, player2))
    val gameController = menuController.startNewGame()
    startGame(gameController)

  }

  /**
    * Starts new game
    */
  def startGame(gameController: GameController): Unit = {
    var isGameOver = false
    //game keeps running until there is a winner
    while (!isGameOver) {
      println("Gameturn Nr. " + gameController.getGameTurns)
      val activePlayer = gameController.getActivePlayer
      println(StringConstants.ACTIVE_PLAYER + activePlayer.name + " (" + activePlayer.color + ") " + StringConstants.ACTIVE_PLAYER_IS_ON_TURN)

      // Print out the playground layout with the Ids of each field and
      // the current playground with all set tokens
      printPlayGroundLayout().onComplete((tried: Try[Unit]) =>
        printCurrentPlayGround(gameController.getGame.playground)
      )(ExecutionContext.global)

      // Check if active player has to set one of his 9 tokens
      // If yes, he has to set a token
      // If no, he has to move or jump (if allowed) with a token
      val tokenPosition = if (gameController.canSetTokens) {
        setToken(gameController)
      } else if (gameController.canJumpTokens) {
        jumpToken(gameController)
      } else {
        moveToken(gameController)
      }

      // 3 tokens in a row => active player is allowed to remove a token from other player
      if (gameController.checkForThreeInARow(tokenPosition)) {
        deleteOpponentToken(gameController)
      }

      //only change the player when there is no winner
      isGameOver = gameController.isGameOver
      if (!isGameOver) {
        gameController.changePlayer()
      }
    }
    //currentplayer has won
    println(StringConstants.GAME_WON_1 + gameController.getActivePlayer.name + StringConstants.GAME_WON_2)
  }

  /**
    * Prints out the playground with all field Ids on the terminal
    */
  def printPlayGroundLayout(): Future[Unit] = {
    Future {
      println(
        "11--------12---------13\n" +
          "|          |          |\n" +
          "|   21----22-----23   |\n" +
          "|   |      |      |   |\n" +
          "|   |  31-32-33   |   |\n" +
          "|   |   |     |   |   |\n" +
          "18--28-38    34--24--14\n" +
          "|   |   |     |   |   |\n" +
          "|   |  37-36-35   |   |\n" +
          "|   |      |      |   |\n" +
          "|   27----26-----25   |\n" +
          "|          |          |\n" +
          "17--------16---------15")
    }(ExecutionContext.global)
  }

  /**
    * Prints out the current playground with all set tokens of the players
    * B stands for all black tokens
    * W stands for all white tokens
    */
  def printCurrentPlayGround(playground: Playground): Future[Unit] = {
    Future {
      val oneOne = getFieldToken(playground, (1, 1))
      val oneTwo = getFieldToken(playground, (1, 2))
      val oneThree = getFieldToken(playground, (1, 3))
      val oneFour = getFieldToken(playground, (1, 4))
      val oneFive = getFieldToken(playground, (1, 5))
      val oneSix = getFieldToken(playground, (1, 6))
      val oneSeven = getFieldToken(playground, (1, 7))
      val oneEight = getFieldToken(playground, (1, 8))
      val twoOne = getFieldToken(playground, (2, 1))
      val twoTwo = getFieldToken(playground, (2, 2))
      val twoThree = getFieldToken(playground, (2, 3))
      val twoFour = getFieldToken(playground, (2, 4))
      val twoFive = getFieldToken(playground, (2, 5))
      val twoSix = getFieldToken(playground, (2, 6))
      val twoSeven = getFieldToken(playground, (2, 7))
      val twoEight = getFieldToken(playground, (2, 8))
      val threeOne = getFieldToken(playground, (3, 1))
      val threeTwo = getFieldToken(playground, (3, 2))
      val threeThree = getFieldToken(playground, (3, 3))
      val threeFour = getFieldToken(playground, (3, 4))
      val threeFive = getFieldToken(playground, (3, 5))
      val threeSix = getFieldToken(playground, (3, 6))
      val threeSeven = getFieldToken(playground, (3, 7))
      val threeEight = getFieldToken(playground, (3, 8))

      println(
        s"$oneOne----------$oneTwo----------$oneThree\n" +
          "|          |          |\n" +
          s"|   $twoOne------$twoTwo------$twoThree   |\n" +
          "|   |      |      |   |\n" +
          s"|   |   $threeOne--$threeTwo--$threeThree   |   |\n" +
          "|   |   |     |   |   |\n" +
          s"$oneEight---$twoEight---$threeEight     $threeFour---$twoFour---$oneFour\n" +
          "|   |   |     |   |   |\n" +
          s"|   |   $threeSeven--$threeSix--$threeFive   |   |\n" +
          "|   |      |      |   |\n" +
          s"|   $twoSeven------$twoSix------$twoFive   |\n" +
          "|          |          |\n" +
          s"$oneSeven----------$oneSix----------$oneFive")
    }(ExecutionContext.global)
  }

  /**
    * Gets the value of the field
    * B for a black token
    * W for a white token
    * . for a free field
    */
  def getFieldToken(playground: Playground, field: (Int, Int)): String = {
    val blackToken = "B"
    val whiteToken = "W"
    val noToken = "."

    // Get the field value
    val fieldValue = playground.fields(field).get()

    if (fieldValue == null) {
      noToken
    } else if (fieldValue.player.color == Color.WHITE) {
      whiteToken
    } else {
      blackToken
    }
  }

  /**
    * Ask active player for setting a token
    */
  def setToken(gameController: GameController): (Int, Int) = {
    val maxTokens = GameConstants.AMOUNT_TOKENS
    val position = getPositionInput(
      StringConstants.SET_TOKEN + " (" + (maxTokens - gameController.getActivePlayer.unsetTokens.get() + 1) + "/" + maxTokens + ")",
      StringConstants.SET_TOKEN_WRONG_POSITION,
      gameController.isPositionFree,
      StringConstants.SET_TOKEN_NO_FREE_POSITION
    )

    // Set token to position
    gameController.setToken(position)
    position
  }

  /**
    * Checks if a string contains always numbers
    */
  def isAllDigits(x: String): Boolean = x forall Character.isDigit

  /**
    * Move token
    */
  def moveToken(gameController: GameController): (Int, Int) = {
    val currentPositionOption = getPositionInput(
      StringConstants.MOVE_TOKEN,
      StringConstants.MOVE_TOKEN_WRONG_POSITION,
      pos => gameController.isPositionSetBy(pos) && gameController.canMove(pos),
      StringConstants.MOVE_TOKEN_FAIL
    )

    getPositionInput(
      StringConstants.MOVE_TOKEN_NEW_POSITION,
      StringConstants.MOVE_TOKEN_WRONG_POSITION,
      gameController.moveToken(currentPositionOption, _),
      StringConstants.MOVE_TOKEN_DESTINATION_FAIL
    )
  }

  /**
    * Move token
    */
  def deleteOpponentToken(gameController: GameController): Unit = {
    printPlayGroundLayout()
    printCurrentPlayGround(gameController.getGame.playground)
    getPositionInput(
      StringConstants.DELETE_OPPONENT_TOKEN,
      StringConstants.SET_TOKEN_WRONG_POSITION,
      gameController.deleteOpponentToken,
      StringConstants.DELETE_OPPONENT_TOKEN_FAIL
    )
  }

  private def getPositionInput(
                                question: String,
                                error: String,
                                additionalCheck: ((Int, Int)) => Boolean = { _ => true },
                                additionalCheckError: String = "NOT USED"
                              ): (Int, Int) = {
    while (true) {
      breakable {
        println(question)
        val inputPosition = checkPositionInput(StdIn.readLine())

        //Wrong position input
        if (inputPosition.isEmpty) {
          println(error)
          break()
        }

        //Perform additional check
        if (!additionalCheck.apply(inputPosition.get)) {
          println(additionalCheckError)
          break()
        }

        return inputPosition.get
      }
    }
    (-1, -1)
  }

  /**
    * Checks if the position input is valid
    */
  private def checkPositionInput(positionInput: String): Option[(Int, Int)] = {

    // Position must contain 2 digits
    if (positionInput.length < 2) {
      return Option.empty
    }

    // Check that all chars of the input string are numbers
    if (!isAllDigits(positionInput)) {
      return Option.empty
    }

    val ring = positionInput.charAt(0)
    val field = positionInput.charAt(1)
    val position = (ring.toString.toInt, field.toString.toInt)

    // Wrong position
    if (position._1 < 1 || position._1 > 3 || position._2 < 1 || position._2 > 8) {
      return Option.empty
    }

    Option(position)
  }

  private def jumpToken(gameController: GameController): (Int, Int) = {
    val currentPositionOption = getPositionInput(
      StringConstants.JUMP_TOKEN,
      StringConstants.JUMP_TOKEN_WRONG_POSITION,
      gameController.isPositionSetBy(_),
      StringConstants.JUMP_TOKEN_FAIL
    )

    getPositionInput(
      StringConstants.JUMP_TOKEN_NEW_POSITION,
      StringConstants.JUMP_TOKEN_WRONG_POSITION,
      gameController.jumpToken(currentPositionOption, _),
      StringConstants.JUMP_TOKEN_DESTINATION_FAIL
    )
  }

  override def start(): Unit = {
    setStartMenuInput()
  }
}
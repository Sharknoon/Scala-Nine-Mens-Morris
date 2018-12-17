package view.tui

import controller.{GameController, MenuController}
import model.{Color, Game, Playground}

class Tui {

  getStartMenuInput()

  def getStartMenuInput(): Unit = {

    // TODO auf leere Eingabe abfragen
    println("Bitte Spielernamen eingeben, um ein neues Spiel zu starten. Bedenke dass der Spieler 1 immer die Farbe weis erhält und damit beginnt.")
    println("Spieler 1:")
    val player1 = scala.io.StdIn.readLine()
    println("Spieler 2:")
    val player2 = scala.io.StdIn.readLine()

    val menuController = new MenuController((player1, player2))
    val gameController = menuController.startNewGame()
    changeTurn(gameController)

  }

  def changeTurn(gameController: GameController): Unit = {

    val activePlayer = gameController.getActivePlayer()
    println("Spieler " + activePlayer.name + " (" + activePlayer.color + ") ist an der Reihe:")
    printPlayGroundLayout()
    printCurrentPlayGround(gameController.getGame().playground)

    if(gameController.canSetTokens(activePlayer)){
      setToken(gameController)
    } else {
      moveToken(gameController)
    }
  }

  def printPlayGroundLayout(): Unit = {
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
  }

  def printCurrentPlayGround(playground: Playground): Unit = {


    val oneOne = getFieldToken(playground, (1,1))
    val oneTwo = getFieldToken(playground, (1,2))
    val oneThree = getFieldToken(playground, (1,3))
    val oneFour = getFieldToken(playground, (1,4))
    val oneFive = getFieldToken(playground, (1,5))
    val oneSix = getFieldToken(playground, (1,6))
    val oneSeven = getFieldToken(playground, (1,7))
    val oneEight = getFieldToken(playground, (1,8))
    val twoOne = getFieldToken(playground, (2,1))
    val twoTwo = getFieldToken(playground, (2,2))
    val twoThree = getFieldToken(playground, (2,3))
    val twoFour = getFieldToken(playground, (2,4))
    val twoFive = getFieldToken(playground, (2,5))
    val twoSix = getFieldToken(playground, (2,6))
    val twoSeven = getFieldToken(playground, (2,7))
    val twoEight = getFieldToken(playground, (2,8))
    val threeOne = getFieldToken(playground, (3,1))
    val threeTwo = getFieldToken(playground, (3,2))
    val threeThree = getFieldToken(playground, (3,3))
    val threeFour = getFieldToken(playground, (3,4))
    val threeFive = getFieldToken(playground, (3,5))
    val threeSix = getFieldToken(playground, (3,6))
    val threeSeven = getFieldToken(playground, (3,7))
    val threeEight = getFieldToken(playground, (3,8))

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
  }

  def getFieldToken(playground: Playground, field : (Int, Int)) : String = {
    val blackToken = "B"
    val whiteToken = "W"
    val noToken = "."

    val fieldValue = playground.fields(field).get()

    if(fieldValue == null){
      noToken
    } else if(fieldValue.player.color == Color.WHITE){
      whiteToken
    } else {
      blackToken
    }
  }

  def setToken(gameController: GameController) : Unit = {
    // TODO Auf Int Eingabe abfragen
    println("Wohin soll der neue Stein gesetzt werden?")
    val positionInput = scala.io.StdIn.readLine()
    val ring = positionInput.charAt(0)
    val field = positionInput.charAt(1)
    val position = (ring.toString.toInt, field.toString.toInt)

    if (!gameController.isPositionFree(position)){
       println("Position ist bereits durch einen anderen Spielstein besetzt!")
      setToken(gameController)
      return
    }
    gameController.setToken(position)
  }

  def moveToken(gameController: GameController): Unit = {

  }
}


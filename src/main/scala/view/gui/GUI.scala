package view.gui

import controller.{GameController, MenuController}
import model.Color.Color
import model._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.binding.Bindings
import scalafx.beans.property.{BooleanProperty, ObjectProperty, StringProperty}
import scalafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.shape.{Circle, LineTo, MoveTo, Path}
import scalafx.scene.text.Font
import scalafx.scene.{Group, Scene}

object GUI extends JFXApp {

  //Main Borderpane with beige background
  private val main = new BorderPane() {
    style = "-fx-base: beige"
  }

  //Sets the state with the title and a icon at a maximized state and the borderpane main
  stage = new JFXApp.PrimaryStage {
    title.value = StringConstants.TITLE
    maximized = true
    icons.add(new Image("logo.png"))
    scene = new Scene {
      root = main
    }
  }

  initStartMenu(main)

  val sizeMultiplier = 100

  val statusText = new StringProperty("Demo")
  val playerText = new StringProperty("Demo")
  private val nextClickAction: ObjectProperty[((Int, Int)) => Unit] = new ObjectProperty[((Int, Int)) => Unit]()

  /**
    * Starts new game
    */
  def startGame(gameController: GameController): Unit = {
    val activePlayer = gameController.getActivePlayer
    playerText.set(StringConstants.ACTIVE_PLAYER + activePlayer.name + " (" + activePlayer.color + ") " + StringConstants.ACTIVE_PLAYER_IS_ON_TURN)

    val callback = (tokenPosition: (Int, Int)) => {

      val callback2 = (_: (Int, Int)) => {

        //only change the player when there is no winner
        val isGameOver = gameController.isGameOver
        if (!isGameOver) {
          gameController.changePlayer()
          startGame(gameController)
        } else {
          //currentplayer has won
          statusText.set(StringConstants.GAME_WON_1 + gameController.getActivePlayer.name + StringConstants.GAME_WON_2)
        }

      }

      // 3 tokens in a row => active player is allowed to remove a token from other player
      if (gameController.checkForThreeInARow(tokenPosition)) {
        deleteOpponentToken(gameController, callback2)
      } else {
        callback2.apply(tokenPosition)
      }
    }

    // Check if active player has to set one of his 9 tokens
    // If yes, he has to set a token
    // If no, he has to move or jump (if allowed) with a token
    if (gameController.canSetTokens) {
      setToken(gameController, callback)
    } else if (gameController.canJumpTokens) {
      jumpToken(gameController, callback)
    } else {
      moveToken(gameController, callback)
    }

  }

  /**
    * Creates the main playground
    *
    * @return The newly created playground
    */
  private def createPlayground(): Group = {
    val group = new Group()

    //The path of the lines
    val path = new Path()
    path.setStrokeWidth(10)
    path.getElements.addAll(
      MoveTo(0, 0),
      LineTo(6 * sizeMultiplier, 0),
      LineTo(6 * sizeMultiplier, 6 * sizeMultiplier),
      LineTo(0, 6 * sizeMultiplier),
      LineTo(0, 0),
      MoveTo(1 * sizeMultiplier, 1 * sizeMultiplier),
      LineTo(5 * sizeMultiplier, 1 * sizeMultiplier),
      LineTo(5 * sizeMultiplier, 5 * sizeMultiplier),
      LineTo(1 * sizeMultiplier, 5 * sizeMultiplier),
      LineTo(1 * sizeMultiplier, 1 * sizeMultiplier),
      MoveTo(2 * sizeMultiplier, 2 * sizeMultiplier),
      LineTo(4 * sizeMultiplier, 2 * sizeMultiplier),
      LineTo(4 * sizeMultiplier, 4 * sizeMultiplier),
      LineTo(2 * sizeMultiplier, 4 * sizeMultiplier),
      LineTo(2 * sizeMultiplier, 2 * sizeMultiplier),
      MoveTo(3 * sizeMultiplier, 0),
      LineTo(3 * sizeMultiplier, 2 * sizeMultiplier),
      MoveTo(6 * sizeMultiplier, 3 * sizeMultiplier),
      LineTo(4 * sizeMultiplier, 3 * sizeMultiplier),
      MoveTo(3 * sizeMultiplier, 6 * sizeMultiplier),
      LineTo(3 * sizeMultiplier, 4 * sizeMultiplier),
      MoveTo(0, 3 * sizeMultiplier),
      LineTo(2 * sizeMultiplier, 3 * sizeMultiplier)
    )

    //Adding the circles
    group.getChildren.addAll(
      path,
      Circle(0, 0, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 0, sizeMultiplier / 5),
      Circle(6 * sizeMultiplier, 0, sizeMultiplier / 5),
      Circle(6 * sizeMultiplier, 3 * sizeMultiplier, sizeMultiplier / 5),
      Circle(6 * sizeMultiplier, 6 * sizeMultiplier, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 6 * sizeMultiplier, sizeMultiplier / 5),
      Circle(0, 6 * sizeMultiplier, sizeMultiplier / 5),
      Circle(0, 3 * sizeMultiplier, sizeMultiplier / 5),

      Circle(1 * sizeMultiplier, 1 * sizeMultiplier, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 1 * sizeMultiplier, sizeMultiplier / 5),
      Circle(5 * sizeMultiplier, 1 * sizeMultiplier, sizeMultiplier / 5),
      Circle(5 * sizeMultiplier, 3 * sizeMultiplier, sizeMultiplier / 5),
      Circle(5 * sizeMultiplier, 5 * sizeMultiplier, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 5 * sizeMultiplier, sizeMultiplier / 5),
      Circle(1 * sizeMultiplier, 5 * sizeMultiplier, sizeMultiplier / 5),
      Circle(1 * sizeMultiplier, 3 * sizeMultiplier, sizeMultiplier / 5),

      Circle(2 * sizeMultiplier, 2 * sizeMultiplier, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 2 * sizeMultiplier, sizeMultiplier / 5),
      Circle(4 * sizeMultiplier, 2 * sizeMultiplier, sizeMultiplier / 5),
      Circle(4 * sizeMultiplier, 3 * sizeMultiplier, sizeMultiplier / 5),
      Circle(4 * sizeMultiplier, 4 * sizeMultiplier, sizeMultiplier / 5),
      Circle(3 * sizeMultiplier, 4 * sizeMultiplier, sizeMultiplier / 5),
      Circle(2 * sizeMultiplier, 4 * sizeMultiplier, sizeMultiplier / 5),
      Circle(2 * sizeMultiplier, 3 * sizeMultiplier, sizeMultiplier / 5)
    )

    group
  }

  def requestTokenPosition(question: String,
                           additionalCheck: ((Int, Int)) => Boolean = { _ => true },
                           additionalCheckError: String = "NOT USED",
                           callback: ((Int, Int)) => Unit): Unit = {

    statusText.set(question)

    nextClickAction.set {
      inputPosition: (Int, Int) => {

        //Perform additional check
        if (!additionalCheck.apply(inputPosition)) {
          statusText.set(additionalCheckError)
          requestTokenPosition(additionalCheckError, additionalCheck, additionalCheckError, callback)
        } else {
          callback.apply(inputPosition)
        }
      }

    }

  }

  def setToken(gameController: GameController, callback: ((Int, Int)) => Unit): Unit = {
    val maxTokens = GameConstants.AMOUNT_TOKENS
    requestTokenPosition(
      StringConstants.SET_TOKEN + " (" + (maxTokens - gameController.getActivePlayer.unsetTokens.get() + 1) + "/" + maxTokens + ")",
      gameController.isPositionFree,
      StringConstants.SET_TOKEN_NO_FREE_POSITION,
      (position: (Int, Int)) => {
        // Set token to position
        gameController.setToken(position)
        callback.apply(position)
      }
    )
  }

  def jumpToken(gameController: GameController, callback: ((Int, Int)) => Unit): Unit = {
    requestTokenPosition(
      StringConstants.JUMP_TOKEN,
      gameController.isPositionSetBy(_),
      StringConstants.JUMP_TOKEN_FAIL,
      (currentPosition: (Int, Int)) => {

        requestTokenPosition(
          StringConstants.JUMP_TOKEN_NEW_POSITION,
          gameController.jumpToken(currentPosition, _),
          StringConstants.JUMP_TOKEN_DESTINATION_FAIL,
          (destinationPosition: (Int, Int)) => {

            callback.apply(destinationPosition)

          }
        )

      }
    )
  }

  private def getCoordinatesFromIndex(index: (Int, Int)): (Int, Int) = {
    val ring = index._1
    val field = index._2

    val x = field match {
      case 1 | 8 | 7 => ring match {
        case 1 => 0
        case 2 => 1
        case 3 => 2
      }
      case 2 | 6 => 3
      case 3 | 4 | 5 => ring match {
        case 3 => 4
        case 2 => 5
        case 1 => 6
      }
    }
    val y = field match {
      case 1 | 2 | 3 => ring match {
        case 1 => 0
        case 2 => 1
        case 3 => 2
      }
      case 8 | 4 => 3
      case 5 | 6 | 7 => ring match {
        case 3 => 4
        case 2 => 5
        case 1 => 6
      }
    }
    (x * sizeMultiplier, y * sizeMultiplier)
  }


  /**
    * This class represents a token in the UI
    */
  private class TokenUI extends Group {
    private val activatedProperty = new BooleanProperty()
    private val outerCircle = new Circle() {
      radius = 30
      style = TokenUI.BLACKSTYLE
    }
    outerCircle.opacity.bind(
      Bindings
        .when(activatedProperty)
        .choose(1)
        .otherwise(
          Bindings
            .when(hover)
            .choose(0.5)
            .otherwise(0)
        )
    )
    children = outerCircle

    def setColor(color: Color): Unit = {
      outerCircle.style = if (color == Color.BLACK) TokenUI.BLACKSTYLE else TokenUI.WHITESTYLE
    }

    def activate(acitvate: Boolean) {
      activatedProperty.set(acitvate)
    }

  }

  object TokenUI {
    val BLACKSTYLE: String = "-fx-fill: radial-gradient(radius 180%, grey, derive(grey, -30%), derive(grey, 30%))"
    val WHITESTYLE: String = "-fx-fill: radial-gradient(radius 180%, white, derive(white, -30%), derive(white, 30%))"
  }

  def moveToken(gameController: GameController, callback: ((Int, Int)) => Unit): Unit = {
    requestTokenPosition(
      StringConstants.MOVE_TOKEN,
      pos => gameController.isPositionSetBy(pos) && gameController.canMove(pos),
      StringConstants.MOVE_TOKEN_FAIL,
      (currentPosition: (Int, Int)) => {

        requestTokenPosition(
          StringConstants.MOVE_TOKEN_NEW_POSITION,
          gameController.moveToken(currentPosition, _),
          StringConstants.MOVE_TOKEN_DESTINATION_FAIL,
          (destinationPosition: (Int, Int)) => {

            callback.apply(destinationPosition)

          }
        )

      }
    )

  }

  def deleteOpponentToken(gameController: GameController, callback: ((Int, Int)) => Unit): Unit = {
    requestTokenPosition(
      StringConstants.DELETE_OPPONENT_TOKEN,
      gameController.deleteOpponentToken,
      StringConstants.DELETE_OPPONENT_TOKEN_FAIL,
      (destinationPosition: (Int, Int)) => {

        callback.apply(destinationPosition)

      }
    )
  }

  /**
    * Shows the start menu
    *
    * @param pane the root pane
    */
  private def initStartMenu(pane: BorderPane): Unit = {
    //The label showing start new game
    val labelTitle = new Label(StringConstants.START_NEW_GAME)
    labelTitle.font = Font.apply(40)

    //Both player labels
    val labelPlayer1 = new Label(StringConstants.PLAYER1)
    val labelPlayer2 = new Label(StringConstants.PLAYER2)

    //Both player names textfields
    val textFieldPlayer1 = new TextField()
    val textFieldPlayer2 = new TextField()

    //The button to start a new game, makes a new menucontroller and starts the game, also shows the playground
    val button = new Button(StringConstants.START_GAME)
    button.onAction = handle {
      val p1 = textFieldPlayer1.getText
      val p2 = textFieldPlayer2.getText
      //Check for correct playernames
      if (p1.isEmpty || p1.isBlank || p2.isEmpty || p2.isBlank) {
        new Alert(AlertType.Error, StringConstants.EMPTY_PLAYER_NAMES).showAndWait()
      } else {
        val menuController = new MenuController((textFieldPlayer1.getText(), textFieldPlayer2.getText))
        val gameController = menuController.startNewGame()
        initPlayground(pane, gameController)
        startGame(gameController)
      }
    }

    //makes all the inputs in a vertical box
    val vbox = new VBox(5)
    vbox.children.addAll(labelTitle, labelPlayer1, textFieldPlayer1, labelPlayer2, textFieldPlayer2, button)

    //setting this vertical box in a group to keep it only as big as necessary
    pane.center = new Group() {
      children = vbox
    }
  }

  /**
    * Creates a new playground
    *
    * @param pane           The pane on which the playground should be created on
    * @param gameController The controller for the logic
    */
  private def initPlayground(pane: BorderPane, gameController: GameController): Unit = {
    //Clearing all the previous children
    pane.children.clear()
    //The new playground in the middle
    val playground = createPlayground()
    pane.center = playground
    //A status label at the bottom
    pane.bottom = createStatusLabel(gameController)
    pane.top = createPlayerLabel(gameController)
    //Binding the tokens to the model
    bindTokens(gameController, playground)
  }

  /**
    * Creates a status label for the playground
    *
    * @return The newly created status label
    */
  private def createStatusLabel(gameController: GameController): Pane = {
    new StackPane() {
      children = new Label() {
        text.bind(statusText)
        font = Font.apply(30)
      }
      margin = Insets(0, 0, 30, 0)
    }
  }

  /**
    * Creates a status label for the playground
    *
    * @return The newly created status label
    */
  private def createPlayerLabel(gameController: GameController): Pane = {
    new StackPane() {
      children = new Label() {
        text.bind(playerText)
        font = Font.apply(35)
      }
      margin = Insets(30, 0, 0, 0)
    }
  }

  /**
    * Binds the tokens to the model
    *
    * @param gameController The controller for the logic
    * @param group          The group in which the playground is located
    */
  private def bindTokens(gameController: GameController, group: Group): Unit = {
    gameController.getGame.playground.fields.foreach((tuple: ((Int, Int), Property[Token])) => {
      val tokenUI = new TokenUI()
      tuple._2.onChange((_, newToken) =>
        if (newToken == null) {
          tokenUI.activate(false)
        } else {
          tokenUI.activate(true)
          tokenUI.setColor(newToken.player.color)
        }
      )
      val coordinates = getCoordinatesFromIndex(tuple._1)
      tokenUI.setLayoutX(coordinates._1)
      tokenUI.setLayoutY(coordinates._2)

      tokenUI.onMouseClicked = handle {
        val action = nextClickAction.value
        if (action != null) {
          action.apply(tuple._1)
        }
        null
      }

      group.children.add(tokenUI)
    })
  }


}
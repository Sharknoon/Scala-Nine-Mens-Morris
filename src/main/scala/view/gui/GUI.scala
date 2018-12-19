package view.gui

import controller.{GameController, MenuController}
import model.Color.Color
import model.{Color, StringConstants, Token}
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
        val game = menuController.startNewGame()
        initPlayground(pane, game)
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
    pane.bottom = createPlaygroundLabel(gameController)
    //Binding the tokens to the model
    bindTokens(gameController, playground)
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

  /**
    * Creates a status label for the playground
    *
    * @return The newly created status label
    */
  private def createPlaygroundLabel(gameController: GameController): Pane = {
    new StackPane() {
      children = new Label() {
        text.bind(
          StringProperty.apply(
            "Demo" // StringConstants.ACTIVE_PLAYER + activePlayer.name + " (" + activePlayer.color + ") " + StringConstants.ACTIVE_PLAYER_IS_ON_TURN
          )
        )
        font = Font.apply(30)
      }
      margin = Insets(0, 0, 30, 0)
    }
  }

  /**
    * Binds the tokens to the model
    *
    * @param gameController The controller for the logic
    * @param group          The group in which the playground is located
    */
  private def bindTokens(gameController: GameController, group: Group): Unit = {
    gameController.getGame.playground.fields.foreach((tuple: ((Int, Int), ObjectProperty[Token])) => {
      val tokenUI = new TokenUI()
      tuple._2.onChange((_, _, newToken) =>
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
        if (gameController.canSetTokens && gameController.isPositionFree(tuple._1)) {
          gameController.setToken(tuple._1)
          gameController.changePlayer()
        }
      }

      group.children.add(tokenUI)
    })
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

}
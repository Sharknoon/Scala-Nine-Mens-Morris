package view.gui

import controller.{GameController, MenuController}
import model.Color.Color
import model.{Color, StringConstants, Token}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.shape.{Circle, LineTo, MoveTo, Path}
import scalafx.scene.text.Font
import scalafx.scene.{Group, Scene, paint}

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
      val menuController = new MenuController((textFieldPlayer1.getText(), textFieldPlayer2.getText))
      val game = menuController.startNewGame()
      initPlayground(pane, game)
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
    pane.bottom = createPlaygroundLabel()
    //Binding the tokens to the model
    bindTokens(gameController, playground)
  }

  /**
    * Creates the main playground
    *
    * @return The newly created playground
    */
  private def createPlayground(): Group = {
    //A handy dandy multiplier for the size
    val multiplier = 100
    val group = new Group()

    //The path of the lines
    val path = new Path()
    path.setStrokeWidth(10)
    path.getElements.addAll(
      MoveTo(0, 0),
      LineTo(6 * multiplier, 0),
      LineTo(6 * multiplier, 6 * multiplier),
      LineTo(0, 6 * multiplier),
      LineTo(0, 0),
      MoveTo(1 * multiplier, 1 * multiplier),
      LineTo(5 * multiplier, 1 * multiplier),
      LineTo(5 * multiplier, 5 * multiplier),
      LineTo(1 * multiplier, 5 * multiplier),
      LineTo(1 * multiplier, 1 * multiplier),
      MoveTo(2 * multiplier, 2 * multiplier),
      LineTo(4 * multiplier, 2 * multiplier),
      LineTo(4 * multiplier, 4 * multiplier),
      LineTo(2 * multiplier, 4 * multiplier),
      LineTo(2 * multiplier, 2 * multiplier),
      MoveTo(3 * multiplier, 0),
      LineTo(3 * multiplier, 2 * multiplier),
      MoveTo(6 * multiplier, 3 * multiplier),
      LineTo(4 * multiplier, 3 * multiplier),
      MoveTo(3 * multiplier, 6 * multiplier),
      LineTo(3 * multiplier, 4 * multiplier),
      MoveTo(0, 3 * multiplier),
      LineTo(2 * multiplier, 3 * multiplier)
    )

    //Adding the circles
    group.getChildren.addAll(
      path,
      Circle(0, 0, multiplier / 5),
      Circle(3 * multiplier, 0, multiplier / 5),
      Circle(6 * multiplier, 0, multiplier / 5),
      Circle(6 * multiplier, 3 * multiplier, multiplier / 5),
      Circle(6 * multiplier, 6 * multiplier, multiplier / 5),
      Circle(3 * multiplier, 6 * multiplier, multiplier / 5),
      Circle(0, 6 * multiplier, multiplier / 5),
      Circle(0, 3 * multiplier, multiplier / 5),

      Circle(1 * multiplier, 1 * multiplier, multiplier / 5),
      Circle(3 * multiplier, 1 * multiplier, multiplier / 5),
      Circle(5 * multiplier, 1 * multiplier, multiplier / 5),
      Circle(5 * multiplier, 3 * multiplier, multiplier / 5),
      Circle(5 * multiplier, 5 * multiplier, multiplier / 5),
      Circle(3 * multiplier, 5 * multiplier, multiplier / 5),
      Circle(1 * multiplier, 5 * multiplier, multiplier / 5),
      Circle(1 * multiplier, 3 * multiplier, multiplier / 5),

      Circle(2 * multiplier, 2 * multiplier, multiplier / 5),
      Circle(3 * multiplier, 2 * multiplier, multiplier / 5),
      Circle(4 * multiplier, 2 * multiplier, multiplier / 5),
      Circle(4 * multiplier, 4 * multiplier, multiplier / 5),
      Circle(4 * multiplier, 4 * multiplier, multiplier / 5),
      Circle(3 * multiplier, 4 * multiplier, multiplier / 5),
      Circle(2 * multiplier, 4 * multiplier, multiplier / 5),
      Circle(2 * multiplier, 3 * multiplier, multiplier / 5)
    )

    group
  }

  /**
    * Creates a status label for the playground
    *
    * @return The newly created status label
    */
  private def createPlaygroundLabel(): Pane = {
    new StackPane() {
      children = new Label() {
        text = "Demo"
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
          tokenUI.setVisible(false)
        } else {
          tokenUI.setVisible(true)
          tokenUI.setColor(newToken.player.color)
        }
      )
      val offset = -1 //TODO game.playground.fields.indexOf(ring)
    })
  }

  private def getCoordinatesFromIndex(index: (Int, Int)): Unit = {
    val ring = index._1
    val field = index._2

  }

  /**
    * This class represents a token in the UI
    */
  private class TokenUI extends Group {
    private val outerCircle = new Circle() {
      radius = 50
      fill = paint.Color.Black
    }
    children = outerCircle

    def setColor(color: Color): Unit = {
      outerCircle.fill = if (color == Color.BLACK) paint.Color.Black else paint.Color.White
    }

  }

}
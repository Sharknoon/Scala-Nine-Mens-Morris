package view.gui

import controller.{GameController, MenuController}
import model.Color.Color
import model.{Color, Game, StringConstants, Token}
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

  private val main = new BorderPane() {
    style = "-fx-base: beige"
  }

  stage = new JFXApp.PrimaryStage {
    title.value = StringConstants.TITLE
    maximized = true
    icons.add(new Image("logo.png"))
    scene = new Scene {
      root = main
    }
  }

  initStartMenu(main)

  private def initStartMenu(pane: BorderPane): Unit = {
    val labelTitle = new Label(StringConstants.START_NEW_GAME)
    labelTitle.font = Font.apply(40)

    val labelPlayer1 = new Label(StringConstants.PLAYER1)
    val labelPlayer2 = new Label(StringConstants.PLAYER2)

    val textFieldPlayer1 = new TextField()
    val textFieldPlayer2 = new TextField()

    val button = new Button(StringConstants.START_GAME)
    button.onAction = handle {
      val menuController = new MenuController((textFieldPlayer1.getText(), textFieldPlayer2.getText))
      val game = menuController.startNewGame()
      initPlayground(pane, game)
    }

    val vbox = new VBox(5)
    vbox.children.addAll(labelTitle, labelPlayer1, textFieldPlayer1, labelPlayer2, textFieldPlayer2, button)

    pane.center = new Group() {
      children = vbox
    }
  }

  private def initPlayground(pane: BorderPane, game: GameController): Unit = {
    pane.children.clear()
    pane.center = createPlayground()
    pane.bottom = createPlaygroundLabel()
    bindTokens(game, pane.center.asInstanceOf[Group])
  }

  private def createPlayground(): Group = {
    val multiplier = 100
    val group = new Group()

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

  private def createPlaygroundLabel(): Pane = {
    new StackPane() {
      children = new Label() {
        text = "Demo"
        font = Font.apply(30)
      }
      margin = Insets(0, 0, 30, 0)
    }
  }

  private def bindTokens(game: GameController, group: Group): Unit = {
    game.getGame().playground.fields.foreach((tuple: ((Int, Int), ObjectProperty[Token])) => {
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

  private class TokenUI extends Group {
    private val outerCircle = new Circle()
    init()

    def setColor(color: Color): Unit = {
      val c = if (color == Color.BLACK) paint.Color.Black else paint.Color.White
      outerCircle.fill = c
    }

    private def init(): Unit = {
      outerCircle.radius = 50
      outerCircle.fill = paint.Color.Black
      this.children = outerCircle
    }

  }

}
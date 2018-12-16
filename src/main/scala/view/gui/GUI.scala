package view.gui

import controller.MenuController
import javafx.event.ActionEvent
import model.StringConstants
import scalafx.application.JFXApp
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.text.Font
import scalafx.scene.{Group, Scene}

object GUI extends JFXApp {

  private val centerContent = new VBox() {
    spacing = 5
  }

  stage = new JFXApp.PrimaryStage {
    title.value = StringConstants.TITLE
    maximized = true
    icons.add(new Image("logo.png"))
    scene = new Scene {
      root = new BorderPane() {
        style = "-fx-base: beige"
        center = new Group() {
          children = centerContent
        }
      }
    }
  }

  initStartMenu(centerContent)

  private def initStartMenu(pane: Pane): Unit = {
    val labelTitle = new Label(StringConstants.START_NEW_GAME)
    labelTitle.font = Font.apply(40)

    val labelPlayer1 = new Label(StringConstants.PLAYER1)
    val labelPlayer2 = new Label(StringConstants.PLAYER2)

    val textFieldPlayer1 = new TextField()
    val textFieldPlayer2 = new TextField()

    val button = new Button(StringConstants.START_GAME)
    button.onAction = (_: ActionEvent) => {
      val startMenu = new MenuController((textFieldPlayer1.getText(), textFieldPlayer2.getText))
      startMenu.startNewGame()
    }

    pane.children.addAll(labelTitle, labelPlayer1, textFieldPlayer1, labelPlayer2, textFieldPlayer2, button)
  }

}
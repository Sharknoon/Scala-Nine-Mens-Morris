package view.gui

import model.StringConstants
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.{Group, Scene}
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.layout.{Border, BorderPane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

object GUI extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = StringConstants.TITLE
    maximized = true
    icons.add(new Image("logo.png"))
    scene = new Scene {
      root = new BorderPane() {
        //fill = Beige
        center = new Group() {
          children = new VBox() {
            spacing = 10
            children.addAll(
              new Label("Spieler 1"),
              new TextField(),
              new Label("Spieler 2"),
              new TextField(),
              new Button("Spiel starten")
            )
          }
        }
      }
    }
  }
}
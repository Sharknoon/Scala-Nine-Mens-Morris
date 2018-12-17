package controller

import model.{Game, Token}
import scalafx.beans.property.ObjectProperty

import scala.util.Try

class GameController(game: Game) {

  def setToken(token: Token, position: (Int, Int)): Try[Boolean] = {
    if (isPositionFree(position)){
      game.playground.fields(position).set(token)
    }
    Try.apply(false)
  }

  def isPositionFree(position: (Int, Int)): Boolean = {
    val field: Option[ObjectProperty[Token]] = game.playground.fields.get(position)
    if (field.isEmpty) {
      return false
    }
    val token: ObjectProperty[Token] = field.get
    token.get() == null
  }

}

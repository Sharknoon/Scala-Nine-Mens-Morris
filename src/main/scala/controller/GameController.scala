package controller

import model.{Game, Token}

import scala.util.Try

class GameController(game: Game) {

  def setToken(token: Token, position: (Int, Int)): Try[Boolean] = {
    if (isPositionFree(position)){
      game.playground.fields.get(position).get.
    }
    Try.apply(false)
  }

  def isPositionFree(position: (Int, Int)): Boolean = {
    game.playground.fields.exists(_ == position -> Option.empty)
  }

}

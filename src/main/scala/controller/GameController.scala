package controller

import model.{Game, Player, Token}
import scalafx.beans.property.ObjectProperty

class GameController(game: Game) {

  private var activePlayer = game.players._1


  def changePlayer() : Player = {

   activePlayer = if(activePlayer == game.players._1) game.players._2 else game.players._1
   activePlayer
  }

  def getActivePlayer() : Player = {
    activePlayer
  }

  def getGame() : Game = {
    game
  }

  def canSetTokens(player: Player): Boolean = {
    player.unsetTokens > 0
  }

  def setToken(position: (Int, Int)): Unit = {
      val newToken = Token(activePlayer)
      game.playground.fields(position).set(newToken)
      activePlayer.unsetTokens-= 1
  }

  def isPositionFree(position: (Int, Int)): Boolean = {
    val field: ObjectProperty[Token] = game.playground.fields(position)
    field.get() == null
  }

  def checkForThreeInARow(position : (Int, Int)) : Boolean = {

    // Corner field
    if(position._2 % 2 == 1){

      // Check two previous tokens
      val firstPredecessor = if((position._2 - 1) % 8 == 0){
        8
      } else {
        (position._2 - 1) % 8
      }

      if(isPositionSetByCurrentPlayer((position._1, firstPredecessor))){
        // Check two previous token
        val secondPredecessor = if((position._2 - 2) % 8 == 0){
          8
        } else {
          (position._2 - 2) % 8
        }

        if(isPositionSetByCurrentPlayer((position._1, secondPredecessor))){
          return  true
        }
      }

      // Check for two successor tokens
      val firstSuccessor = if((position._2 + 1) % 8 == 0){
        8
      } else {
        (position._2 + 1) % 8
      }

      if(isPositionSetByCurrentPlayer((position._1, firstSuccessor))){
        // Check two previous token
        val secondSuccessor = if((position._2 + 2) % 8 == 0){
          8
        } else {
          (position._2 + 2) % 8
        }

        if(isPositionSetByCurrentPlayer((position._1, secondSuccessor))){
          return  true
        }
      }

    // Middle field
    } else {

    }

    return  false


  }

  def isPositionSetByCurrentPlayer(position : (Int, Int)) : Boolean = {
    val token = game.playground.fields(position).get()
    if(token == null){
      return  false
    }
    token.player == activePlayer
  }
}

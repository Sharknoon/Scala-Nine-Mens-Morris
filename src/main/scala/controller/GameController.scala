package controller

import model.{Game, Player, Token}
import scalafx.beans.property.ObjectProperty

class GameController(game: Game) {

  private var activePlayer = game.players._1

  /*
    Change the active player in case that the turn of the current player
    is over
   */
  def changePlayer() : Player = {
   activePlayer = if(activePlayer == game.players._1) game.players._2 else game.players._1
   activePlayer
  }

  /*
    Returns the current active player
   */
  def getActivePlayer: Player = {
    activePlayer
  }

  /*
     Returns the current active game
   */
  def getGame: Game = {
    game
  }

  /*
    Checks if a player has already set all his tokens or not
    at the beginning of the game
   */
  def canSetTokens(player: Player): Boolean = {
    player.unsetTokens > 0
  }

  /*
    Set a token of a player to the playground at the beginning of the game
    Each player has 9 tokens to set
   */
  def setToken(position: (Int, Int)): Unit = {
      val newToken = Token(activePlayer)
      game.playground.fields(position).set(newToken)
      activePlayer.unsetTokens-= 1
  }

  /*
    Checks if the position where the token of the player should be set is free
    or already occupied by another token
    In case that no token occupied on the field, the object property is null
   */
  def isPositionFree(position: (Int, Int)): Boolean = {
    val field: ObjectProperty[Token] = game.playground.fields(position)
    field.get() == null
  }

  /*
    Checks if the active player gets 3 tokens in a row after he sets or move a token
    In this case the active player is allowed to remove one token from the other player
   */
  def checkForThreeInARow(position : (Int, Int)) : Boolean = {

    // Corner field
    // New position of the moved token is in a corner (all odd fields)
    if(position._2 % 2 == 1){

      // Check two previous tokens
      // Exception field 1 => in this case the previous token is 8
      // In all other case the previous tokens can be calculate by modulo 8
      val firstPredecessor = if((position._2 - 1) % 8 == 0){
        8
      } else {
        (position._2 - 1) % 8
      }

      // The previous token has the same color as the active player,
      // check the second previous token
      if(isPositionSetByCurrentPlayer((position._1, firstPredecessor))){
        // Check two previous token
        // Exception field 1 => in this case the previous token is 8
        // In all other case the previous tokens can be calculate by modulo 8
        val secondPredecessor = if((position._2 - 2) % 8 == 0){
          8
        } else {
          (position._2 - 2) % 8
        }

        // The second previous token has also the same color as the active player
        // 3 same color tokens are in one row
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

      // The successor token has the same color as the active player,
      // check the second successor token
      if(isPositionSetByCurrentPlayer((position._1, firstSuccessor))){
        // Check two previous token
        // Exception field 1 => in this case the previous token is 8
        // In all other case the previous tokens can be calculate by modulo 8
        val secondSuccessor = if((position._2 + 2) % 8 == 0){
          8
        } else {
          (position._2 + 2) % 8
        }

        // The second successor token has also the same color as the active player
        // 3 same color tokens are in one row
        if(isPositionSetByCurrentPlayer((position._1, secondSuccessor))){
          return  true
        }
      }

    // Middle field
    // New position of the moved token is in the middle (all even fields)
    } else {

    }

      false


  }

  /*
      Checks if the token of the hand over position has the same color as the active player
   */
  def isPositionSetByCurrentPlayer(position : (Int, Int)) : Boolean = {
    val token = game.playground.fields(position).get()
    if(token == null){
      return  false
    }
    token.player == activePlayer
  }
}

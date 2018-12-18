package controller

import model.{Game, Player, Token}
import scalafx.beans.property.ObjectProperty

class GameController(game: Game) {

  private var activePlayer = game.players._1

  /**
    * Change the active player in case that the turn of the current player
    * is over
    */
  def changePlayer(): Player = {
    activePlayer = if (activePlayer == game.players._1) game.players._2 else game.players._1
    activePlayer
  }

  /**
    * Returns the current active player
    */
  def getActivePlayer: Player = {
    activePlayer
  }

  /**
    * Returns the current active game
    */
  def getGame: Game = {
    game
  }

  /**
    * Checks if a player has already set all his tokens or not
    * at the beginning of the game
    */
  def canSetTokens(player: Player): Boolean = {
    player.unsetTokens > 0
  }

  /**
    * Set a token of a player to the playground at the beginning of the game
    * Each player has 9 tokens to set
    */
  def setToken(position: (Int, Int)): Unit = {
    val newToken = Token(activePlayer)
    game.playground.fields(position).set(newToken)
    activePlayer.unsetTokens -= 1
  }

  /**
    * Checks if the position where the token of the player should be set is free
    * or already occupied by another token
    * In case that no token occupied on the field, the object in the object property is null
    */
  def isPositionFree(position: (Int, Int)): Boolean = {
    val field: ObjectProperty[Token] = game.playground.fields(position)
    field.get() == null
  }

  /**
    * Checks if the active player gets 3 tokens in a row after he sets or move a token
    * In this case the active player is allowed to remove one token from the other player
    */
  def checkForThreeInARow(position: (Int, Int)): Boolean = {

    // Corner field
    // New position of the moved token is in a corner (all odd fields)
    if (mod(position._2, 2) == 1) {

      // Check two previous tokens
      // Exception field 1 => in this case the previous token is 8
      // In all other case the previous tokens can be calculate by modulo 8
      val firstPredecessor = getRingField(position._2, _ - 1)

      // The previous token has the same color as the active player,
      // check the second previous token
      if (isPositionSetByCurrentPlayer((position._1, firstPredecessor))) {
        // Check two previous token
        // Exception field 1 => in this case the previous token is 8
        // In all other case the previous tokens can be calculate by modulo 8
        val secondPredecessor = getRingField(position._2, _ - 2)

        // The second previous token has also the same color as the active player
        // 3 same color tokens are in one row
        if (isPositionSetByCurrentPlayer((position._1, secondPredecessor))) {
          return true
        }
      }

      // Check for two successor tokens
      val firstSuccessor = getRingField(position._2, _ + 1)

      // The successor token has the same color as the active player,
      // check the second successor token
      if (isPositionSetByCurrentPlayer((position._1, firstSuccessor))) {
        // Check two previous token
        // Exception field 1 => in this case the previous token is 8
        // In all other case the previous tokens can be calculate by modulo 8
        val secondSuccessor = getRingField(position._2, _ + 2)

        // The second successor token has also the same color as the active player
        // 3 same color tokens are in one row
        if (isPositionSetByCurrentPlayer((position._1, secondSuccessor))) {
          return true
        }
      }

      // Middle field
      // New position of the moved token is in the middle (all even fields)
    } else {

      //The left neighbour of the token in the ring
      val left = getRingField(position._2, _ - 1)

      //Check for the left neighbour, only then the check for the right neighbour is necessary
      if (isPositionSetByCurrentPlayer((position._1, left))) {

        //The right neighbour of the token in the ring
        val right = getRingField(position._2, _ + 1)

        //This means that both neighbours have the same color as this token
        if (isPositionSetByCurrentPlayer((position._1, right))) {
          return true
        }
      }

      //Check for 3 same colors of the tokens between the rings
      //These are the rings to be checked against
      val positionsToCheck = position._1 match {
        case 1 => (2, 3)
        case 2 => (1, 3)
        case 3 => (1, 2)
        //shouldn't happen
        case _ => (-1, -1)
      }

      //Check the first ring
      if (isPositionSetByCurrentPlayer((positionsToCheck._1, position._2))) {

        //Check second ring
        if (isPositionSetByCurrentPlayer((positionsToCheck._2, position._2))) {
          return true
        }
      }

    }
    false
  }

  /**
    *
    * @param field    The field (index inside the ring)
    * @param modifier The modifier of the field (e.g. 1 for the successor or -2 for the pre-predecessor)
    * @return the index of the token of the requested field item (before 1 is 8)
    */
  private def getRingField(field: Int, modifier: Int => Int): Int = {
    //We dont have a 0 index so we must handle this special case
    if (mod(modifier.apply(field), 8) == 0) {
      8
    } else {
      mod(modifier.apply(field), 8)
    }
  }

  /**
    * Checks if the token of the hand over position has the same color as the active player
    */
  def isPositionSetByCurrentPlayer(position: (Int, Int)): Boolean = {
    val token = game.playground.fields(position).get()
    if (token == null) {
      return false
    }
    token.player == activePlayer
  }
}

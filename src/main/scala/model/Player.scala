package model

import model.Color.Color

case class Player(name: String,
                  color: Color,
                  var unsetTokens : Int = GameConstants.AMOUNT_TOKENS)




package model

import model.Color.Color

case class Player(name: String,
                  color: Color,
                  unsetTokens: Property[Int] = new Property(
                    value = GameConstants.AMOUNT_TOKENS
                  ),
                  tokensInGame: Property[Int] = new Property(0))




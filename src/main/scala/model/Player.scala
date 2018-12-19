package model

import model.Color.Color
import scalafx.beans.property.IntegerProperty

case class Player(name: String,
                  color: Color,
                  unsetTokens: IntegerProperty = new IntegerProperty() {
                    value = GameConstants.AMOUNT_TOKENS
                  },
                  tokensInGame: IntegerProperty = new IntegerProperty())




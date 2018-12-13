package model

import model.Color.Color

case class Player(name: String,
                  var tokens: List[Token] = List(),
                  color: Color) {
  tokens = List.fill(GameConstants.AMOUNT_TOKENS)(Token(this))
}




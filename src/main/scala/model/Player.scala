package model

import model.Color.Color

case class Player(name: String,
                  var tokens: List[Token],
                  color: Color)




package model

import scalafx.beans.property.ObjectProperty

/**
  * Rings
  * 0  1  2
  * 7     3
  * 6  5  4
  *
  * Layers
  * 1  1  1  1  1
  * 1  2  2  2  1
  * 1  2  3  2  1
  * 1  2  2  2  1
  * 1  1  1  1  1
  *
  * @param fields the fields, no need to be set
  */
case class Playground(var fields: Map[(Int, Int), ObjectProperty[Token]] = Map.empty) {
  fields = Map(
    (1, 1) -> new ObjectProperty[Token](),
    (1, 2) -> new ObjectProperty[Token](),
    (1, 3) -> new ObjectProperty[Token](),
    (1, 4) -> new ObjectProperty[Token](),
    (1, 5) -> new ObjectProperty[Token](),
    (1, 6) -> new ObjectProperty[Token](),
    (1, 7) -> new ObjectProperty[Token](),
    (1, 8) -> new ObjectProperty[Token](),

    (2, 1) -> new ObjectProperty[Token](),
    (2, 2) -> new ObjectProperty[Token](),
    (2, 3) -> new ObjectProperty[Token](),
    (2, 4) -> new ObjectProperty[Token](),
    (2, 5) -> new ObjectProperty[Token](),
    (2, 6) -> new ObjectProperty[Token](),
    (2, 7) -> new ObjectProperty[Token](),
    (2, 8) -> new ObjectProperty[Token](),

    (3, 1) -> new ObjectProperty[Token](),
    (3, 2) -> new ObjectProperty[Token](),
    (3, 3) -> new ObjectProperty[Token](),
    (3, 4) -> new ObjectProperty[Token](),
    (3, 5) -> new ObjectProperty[Token](),
    (3, 6) -> new ObjectProperty[Token](),
    (3, 7) -> new ObjectProperty[Token](),
    (3, 8) -> new ObjectProperty[Token]()
  )
}
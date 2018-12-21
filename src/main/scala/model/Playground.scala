package model

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
case class Playground(var fields: Map[(Int, Int), Property[Token]] = Map.empty) {
  fields = Map(
    (1, 1) -> new Property[Token](null),
    (1, 2) -> new Property[Token](null),
    (1, 3) -> new Property[Token](null),
    (1, 4) -> new Property[Token](null),
    (1, 5) -> new Property[Token](null),
    (1, 6) -> new Property[Token](null),
    (1, 7) -> new Property[Token](null),
    (1, 8) -> new Property[Token](null),

    (2, 1) -> new Property[Token](null),
    (2, 2) -> new Property[Token](null),
    (2, 3) -> new Property[Token](null),
    (2, 4) -> new Property[Token](null),
    (2, 5) -> new Property[Token](null),
    (2, 6) -> new Property[Token](null),
    (2, 7) -> new Property[Token](null),
    (2, 8) -> new Property[Token](null),

    (3, 1) -> new Property[Token](null),
    (3, 2) -> new Property[Token](null),
    (3, 3) -> new Property[Token](null),
    (3, 4) -> new Property[Token](null),
    (3, 5) -> new Property[Token](null),
    (3, 6) -> new Property[Token](null),
    (3, 7) -> new Property[Token](null),
    (3, 8) -> new Property[Token](null)
  )
}
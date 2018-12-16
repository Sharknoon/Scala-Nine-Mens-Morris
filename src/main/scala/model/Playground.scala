package model

case class Playground(var fields: Map[(Int, Int), Option[Token]] = Map.empty) {
  fields = Map(
    (1, 1) -> Option.empty,
    (1, 2) -> Option.empty,
    (1, 3) -> Option.empty,
    (1, 4) -> Option.empty,
    (1, 5) -> Option.empty,
    (1, 6) -> Option.empty,
    (1, 7) -> Option.empty,
    (1, 8) -> Option.empty,

    (2, 1) -> Option.empty,
    (2, 2) -> Option.empty,
    (2, 3) -> Option.empty,
    (2, 4) -> Option.empty,
    (2, 5) -> Option.empty,
    (2, 6) -> Option.empty,
    (2, 7) -> Option.empty,
    (2, 8) -> Option.empty,

    (3, 1) -> Option.empty,
    (3, 2) -> Option.empty,
    (3, 3) -> Option.empty,
    (3, 4) -> Option.empty,
    (3, 5) -> Option.empty,
    (3, 6) -> Option.empty,
    (3, 7) -> Option.empty,
    (3, 8) -> Option.empty
  )
}
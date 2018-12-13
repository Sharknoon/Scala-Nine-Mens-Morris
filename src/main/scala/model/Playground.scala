package model

case class Playground(playground: List[List[Option[Token]]] = List.fill(3)(List.fill(8)(Option.empty)))
package actors

import akka.actor.Actor

class TurnCounter extends Actor {
  var turncounter = 0

  override def receive: PartialFunction[Any, Unit] = {
    case "incr" => turncounter += 1
    case "get" => sender ! turncounter
  }
}

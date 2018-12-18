package object controller {
  def mod(a: Int, b: Int): Int = {
    if (a >= 0) a % b else (a % b) + b
  }
}

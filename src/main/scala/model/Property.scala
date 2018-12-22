package model

class Property[T](var value: T) {

  var onChange: Option[(T, T) => Unit] = Option.empty


  def onChange(function: (T, T) => Unit): Unit = onChange = Option(function)

  def get(): T = {
    value
  }

  def set(value: T): Unit = {
    if (onChange.nonEmpty) {
      onChange.get.apply(this.value, value)
    }
    this.value = value

  }

}

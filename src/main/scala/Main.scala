import com.google.inject.{Guice, Inject}
import view.{UI, UIModule}

object Main {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new UIModule)

    val ui = injector.getInstance(classOf[NineMensMorris])
    ui.start
  }

  class NineMensMorris @Inject()(ui: UI) {
    def start: Unit = {
      ui.start()
    }
  }

}

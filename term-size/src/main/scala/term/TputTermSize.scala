package term

import scala.sys.process.Process
import scala.sys.process.ProcessLogger

object TputTermSize extends TermSize {
  override def rawSize(): Int = {
    Process(
      "sh" :: "-c" :: """echo "cols\nlines" | tput -S 2>/dev/tty""" :: Nil
    )
      .!!(silentLogger)
      .linesIterator
      .toList match {
      case cols :: rows :: Nil => encode(cols, rows)
      case _                   => encode(-1, -1)
    }
  }

  override def available(): Boolean =
    sys.env.contains("TERM") &&
      Process("sh" :: "-c" :: "command -v pdflatex 1> /dev/null" :: Nil)
        .!<(silentLogger) == 0

  private def encode(cols: String, rows: String): Int =
    try encode(cols.toInt, rows.toInt)
    catch { case _: NumberFormatException => encode(-1, -1) }

  private val silentLogger = new ProcessLogger {
    override def out(s: => String): Unit = println(s)
    override def err(s: => String): Unit = println(s)
    override def buffer[T](f: => T): T   = f
  }
}

package term

import scala.util.Try

trait TermSize {
  def available(): Boolean
  def rawTermSize(): Int

  def colsAndRows(): (Int, Int) = {
    val encoded = rawTermSize()
    decode(encoded)
  }

  def cols(): Int =
    cols(rawTermSize())

  def rows(): Int =
    rows(rawTermSize())

  @inline protected def cols(encoded: Int): Int =
    encoded >> 16

  @inline protected def rows(encoded: Int): Int =
    encoded << 16 >> 16

  @inline protected def decode(encoded: Int): (Int, Int) =
    (cols(encoded), rows(encoded))

  @inline protected def encode(cols: Int, rows: Int): Int =
    cols << 16 | rows
}

object TermSize extends TermSize {
  private val provider: TermSize = getTermSize()

  override def available(): Boolean = true
  override def rawTermSize(): Int   = provider.rawTermSize()

  def colsAndRowsOrElse(cols: Int, rows: Int): (Int, Int) = {
    if (provider == NoTermSize) (cols, rows)
    else colsAndRows()
  }

  private def getTermSize(): TermSize = {
    Try { new NativeTermSize() }
      .filter(_.available())
      .getOrElse {
        if (TputTermSize.available()) TputTermSize
        else NoTermSize
      }
  }
}

package term

/**
 * A terminal size, in columns and rows.
 *
 * @param cols The number of columns of the current terminal.
 * @param rows The number of rows of the current terminal.
 */
final case class Size(cols: Int, rows: Int)

object Size {
  val NoSize: Size = Size(-1, -1)
}

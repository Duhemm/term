package term

/**
 * A provider that is capable of providing the size of the current terminal.
 */
trait TermSize {

  /**
   * Is this provider available? A provider may be unavailable because a native library may be
   * missing for a given operating system, or an external program may be missing.
   *
   * @return true if this provider can be used to get the size of the terminal, false otherwise.
   */
  def available(): Boolean

  /**
   * The size of the current terminal in columns and rows, encoded as a single integer:
   *
   * ```
   * cols << 16 | (rows & 0xFFFF)
   * ```
   *
   * @return The encoded size of the current terminal.
   */
  def rawSize(): Int

  /**
   * The size of the current terminal.
   *
   * @return The size of the current terminal.
   */
  def size(): Size = {
    val encoded = rawSize()
    Size(cols(encoded), rows(encoded))
  }

  /**
   * The number of columns of the current terminal.
   *
   * @return The number of columns of the current terminal.
   */
  def cols(): Int =
    cols(rawSize())

  /**
   * The number of rows of the current terminal.
   *
   * @return The number of rows of the current terminal.
   */
  def rows(): Int =
    rows(rawSize())

  /**
   * Decode the number of columns from `encoded`.
   *
   * @param encoded The encoded size of the terminal.
   * @return The number of columns.
   */
  @inline protected def cols(encoded: Int): Int =
    encoded >>> 16

  /**
   * Decode the number of rows from `encoded`.
   *
   * @param encoded The encoded size of the terminal.
   * @return The number of rows.
   */
  @inline protected def rows(encoded: Int): Int =
    encoded << 16 >>> 16

  /**
   * Encode the number of columns and rows into a single integer.
   *
   * @param cols The number of columns.
   * @param rows The number of rows.
   * @return The 2 values, encoded in a single integer.
   */
  @inline protected def encode(cols: Int, rows: Int): Int =
    cols << 16 | (rows & 0xffff)
}

object TermSize extends TermSize {
  private val provider: TermSize = getTermSize()

  override def available(): Boolean = provider.available()
  override def rawSize(): Int       = provider.rawSize()

  def size(orElse: => Size): Size = {
    if (provider == NoTermSize) orElse
    else size()
  }

  private def getTermSize(): TermSize = {
    if (NativeTermSize.available()) NativeTermSize
    else if (TputTermSize.available()) TputTermSize
    else NoTermSize
  }
}

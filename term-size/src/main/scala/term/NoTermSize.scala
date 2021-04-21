package term

/**
 * A provider that always return `Size.NoSize`.
 */
object NoTermSize extends TermSize {
  override def available(): Boolean = true
  override def size(): Size         = Size.NoSize
  override def rawSize(): Int       = encode(-1, -1)
}

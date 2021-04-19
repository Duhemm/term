package term

object NoTermSize extends TermSize {
  override def available(): Boolean = true
  override def rawTermSize(): Int   = encode(-1, -1)
}

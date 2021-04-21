package term

import ch.jodersky.jni.nativeLoader

/**
 * A provider that gets the current terminal size using a native library.
 */
object NativeTermSize extends TermSize {
  private val provider =
    try new NativeTermSize
    catch { case _: UnsatisfiedLinkError => null }
  override def available(): Boolean = provider != null
  override def rawSize(): Int       = provider.rawSize()

}

@nativeLoader("term-size")
private class NativeTermSize {
  @native def rawSize(): Int
}

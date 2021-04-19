package term

import ch.jodersky.jni.nativeLoader

@nativeLoader("term-size")
class NativeTermSize extends TermSize {
  override def available(): Boolean = colsAndRows() != (-1, -1)
  @native def rawTermSize(): Int
}

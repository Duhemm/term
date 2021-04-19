package term

class NativeTermSizeSuite extends BaseTermSizeSuite(new NativeTermSize) {
  testIfAvailable("NativeTermSize returns the term size") { case (actual, ts) =>
    assert(ts.colsAndRows() == actual)
  }
}

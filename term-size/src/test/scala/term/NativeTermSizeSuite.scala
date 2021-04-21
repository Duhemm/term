package term

class NativeTermSizeSuite extends BaseTermSizeSuite(NativeTermSize) {
  testIfAvailable("NativeTermSize returns the term size") { case (actual, ts) =>
    assert(ts.size() == actual)
  }
}

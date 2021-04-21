package term

class TermSizeSuite extends BaseTermSizeSuite(TermSize) {
  testIfAvailable("TermSize returns the term size") { case (actual, ts) =>
    assert(ts.size() == actual)
  }

}

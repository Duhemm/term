package term

class TputTermSizeSuite extends BaseTermSizeSuite(TputTermSize) {
  testIfAvailable("tput returns the terminal size") { case (actual, ts) =>
    assert(ts.size() == actual)
  }
}

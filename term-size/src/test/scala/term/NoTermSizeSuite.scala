package term

class NoTermSizeSuite extends BaseTermSizeSuite(NoTermSize) {
  test("NoTermSize returns NoSize") {
    assert(NoTermSize.size() == Size.NoSize)

  }
}

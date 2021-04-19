package term

class NoTermSizeSuite extends BaseTermSizeSuite(NoTermSize) {
  test("NoTermSize returns (-1, -1)") {
    assert(NoTermSize.colsAndRows() == (-1, -1))
  }
}

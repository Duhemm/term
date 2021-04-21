package term

import scala.io.Source
import scala.util.control.NonFatal

import org.scalatest.funsuite.AnyFunSuite

abstract class BaseTermSizeSuite(ts: => TermSize) extends AnyFunSuite {

  val actualSize: Option[Size] =
    try {
      val stream            = getClass.getClassLoader.getResourceAsStream("size")
      val Array(cols, rows) = Source.fromInputStream(stream).mkString.split(' ')
      Some(Size(cols.toInt, rows.toInt))
    } catch {
      case NonFatal(e) =>
        None
    }

  private def ignored(reason: String): Unit = info(
    s"${getClass.getName} is ignored: $reason"
  )

  private val provider =
    try {
      if (ts.available()) Some(ts)
      else { ignored("unavailable"); None }
    } catch {
      case _: UnsatisfiedLinkError =>
        ignored("missing native library")
        None
    }

  def testIfAvailable(
      name: String
  )(body: (Size, TermSize) => Unit): Unit = test(name) {
    actualSize match {
      case None         => ignored("actual size unknown")
      case Some(actual) => provider.foreach(body(actual, _))
    }
  }
}

# term-size - How large is the terminal window?

`term-size` exposes a platform-independent API to get the size of the current terminal window.
On platform where the native library is available, `term-size` doesn't require forking a new
process to get the terminal size. When the native library is unavailable, `term-size` uses
`tput` to get the size of the current terminal. 

## Installation

`term-size` is available on Maven Central. Add the following to your build:

```scala
libraryDependencies += "com.github.duhemm" %% "term-size" % "<version>"
```

The latest version available is ![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.duhemm/term-size_2.12/badge.svg)

## Example usage

```scala
object Main {
  def main(args: Array[String]): Unit = {
    val size = term.TermSize.size()
    println(s"Current terminal size: ${size.rows} rows and ${size.cols} columns.")
  }
}
```

Documentation: [![javadoc](https://javadoc.io/badge2/com.github.duhemm/term-size_2.12/javadoc.svg)](https://javadoc.io/doc/com.github.duhemm/term-size_2.12)

## Compatibility

| Operating system / CPU architecture | x86_64 | x86_32 | ARM |
|-------------------------------------|:------:|:------:|:---:|
| MacOS                               |    🚀   |    ✅   |  ✅  |
| Linux                               |    🚀   |    ✅   |  ✅  |
| Windows                             |    🚀   |    ❌   |  ❌  |

🚀: Available, native  
✅: Available, via Tput  
❌: Unavailable

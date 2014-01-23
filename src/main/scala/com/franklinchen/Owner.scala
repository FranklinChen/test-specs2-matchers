package com.franklinchen

import scala.util._

class Owner(name: String) {
  var state = "name"

  def run(other: String): Try[String] = {
    if (other == name) {
      Success(s"$other is good")
    } else {
      state += ": error processing"
      Failure(new NullPointerException)
    }
  }

  /** Depends on internal state. */
  def formatError(t: Throwable): String = {
    state
  }
}

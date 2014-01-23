package com.franklinchen

import org.specs2._
import org.specs2.matcher._
import scala.util._

object WrappedMatchers {
  type Result = (Try[String], Owner)

  implicit class WrapOwner(val owner: Owner) extends AnyVal {
    def testRun(arg: String): Result = {
      (owner.run(arg), owner)
    }
  }
}

// TODO description is not what we actually want
// The implicit solution seems cleanest
trait WrappedMatchers extends TraversableMatchers {
  import WrappedMatchers._

  case object beGood extends Matcher[Result] {
    def apply[S <: Result](s: Expectable[S]) = {
      s.value match {
        case (Success(_), owner) =>
          result(
            true,
            s"${s.description} is a Success",
            s"${s.description} is not a Success",
            s
          )
        case (Failure(f), owner) =>
          result(
            false,
            s"${s.description} is not a Failure",
            s"${s.description}: ${owner.formatError(f)}",
            s
          )
      }
    }
  }

  case object beBad extends Matcher[Result] {
    def apply[S <: Result](s: Expectable[S]) = {
      s.value match {
        case (Failure(f), owner) =>
          result(
            true,
            s"${s.description}: ${owner.formatError(f)}",
            s"${s.description} is not a Failure",
            s
          )
        case (Success(_), owner) =>
          result(
            false,
            s"${s.description} is not a Success",
            s"${s.description} is a Success",
            s
          )
      }
    }
  }
}
package com.franklinchen

import org.specs2._
import org.specs2.matcher._
import scala.util.{Try, Success, Failure}

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

  def formatError(t: Throwable): String = {
    state
  }
}

trait MyMatchers extends TraversableMatchers {
  def beGood(implicit owner: Owner) = GoodMatcher()

  def beBad(implicit owner: Owner) = BadMatcher()

  case class GoodMatcher(implicit owner: Owner) extends Matcher[Try[String]] {
    def apply[S <: Try[String]](s: Expectable[S]) = {
      s.value match {
        case Success(_) =>
          result(
            true,
            s"${s.description} is a Success",
            s"${s.description} is not a Success",
            s
          )
        case Failure(f) =>
          result(
            false,
            s"${s.description} is not a Failure",
            s"${s.description}: ${owner.formatError(f)}",
            s
          )
      }
    }
  }

  case class BadMatcher(implicit owner: Owner) extends Matcher[Try[String]] {
    def apply[S <: Try[String]](s: Expectable[S]) = {
      s.value match {
        case Failure(f) =>
          result(
            true,
            s"${s.description}: ${owner.formatError(f)}",
            s"${s.description} is not a Failure",
            s
          )
        case Success(_) =>
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
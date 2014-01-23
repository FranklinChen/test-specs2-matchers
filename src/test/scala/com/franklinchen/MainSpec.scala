package com.franklinchen

import org.specs2._

class MainSpec extends Specification with MyMatchers { def is = s2"""
  Good result is good $e1
  Bad result is good? $e2

  Good result is bad? $e3
  Bad result is bad $e4

  Stuff $e5
  Setting message $e6
  """

  def e1 = {
    implicit val owner = new Owner("1")
    owner.run("1") must beGood
  }

  def e2 = {
    implicit val owner = new Owner("2")
    owner.run("000") must beGood
  }

  def e3 = {
    implicit val owner = new Owner("3")
    owner.run("3") must beBad
  }

  def e4 = {
    implicit val owner = new Owner("4")
    owner.run("000") must beBad
  }

  def beFive =
    be_===(5).setMessage("this message has been set!!")

  def e5 = {
    4 must beFive
  }

  import scala.util.{Try, Success, Failure}
  import org.specs2.matcher._
  def beUsefullyGood(implicit owner: Owner): Matcher[Try[String]] = {
    s: Try[String] => (
      s.isSuccess,
      s match {
        // Note sloppiness here.
        case Success(_) => ???
        case Failure(f) => owner.formatError(f)
      }
    )
  }

  def e6 = {
    implicit val owner = new Owner("2")
    owner.run("000") must beUsefullyGood
  }
}

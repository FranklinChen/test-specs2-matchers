package com.franklinchen

import org.specs2._

class WrappedSpec extends Specification with WrappedMatchers { def is = s2"""
  Good result is good $e1
  Bad result is good? $e2

  Good result is bad? $e3
  Bad result is bad $e4
  """

  import WrappedMatchers._

  def e1 = {
    val owner = new Owner("1")
    owner.testRun("1") must beGood
  }

  def e2 = {
    val owner = new Owner("2")
    owner.testRun("000") must beGood
  }

  def e3 = {
    val owner = new Owner("3")
    owner.testRun("3") must beBad
  }

  def e4 = {
    val owner = new Owner("4")
    owner.testRun("000") must beBad
  }
}

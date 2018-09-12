/*
 * Copyright (C) 2009-2011 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled.scala

import org.testng.annotations.Test
import org.scalatest.testng.TestNGSuiteLike
import org.parboiled.errors.ParserRuntimeException
import testing.ParboiledTest

class EOIMatchingTest extends ParboiledTest with TestNGSuiteLike {
  // def fail(message: String): Nothing = super.fail(message)(null)

  class EOIMatchingParser extends Parser {
    def Clause = rule { oneOrMore(EOI) }
  }

  @Test(
    expectedExceptions = Array(classOf[ParserRuntimeException]),
    expectedExceptionsMessageRegExp = "Parser read more than 100K chars beyond EOI, " +
      "verify that your grammar does not consume EOI indefinitely!"
  )
  def testEOIMatchingParser(): Unit = {
    val rule = new EOIMatchingParser().Clause
    parse(ReportingParseRunner(rule), "") {
      fail("Exception expected")
    }
  }

}
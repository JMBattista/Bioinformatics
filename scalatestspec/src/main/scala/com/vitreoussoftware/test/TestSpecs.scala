/**
 * Copyright (C) 2009-2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vitreoussoftware.test

import org.scalatest._
import org.scalatest.prop.PropertyChecks

/**
 * Base for setting up tests using ScalaTest
 */
abstract class UnitSpec extends FlatSpec with Matchers with Inside with OptionValues with EitherValues with Inspectors

/**
 * Base for Property based testing
 */
abstract class PropertySpec extends UnitSpec with PropertyChecks

/**
 * Describe / it syntax Behavioral tests
 */
abstract class BehaviorSpec extends fixture.FunSpec  with Matchers with Inside with OptionValues with EitherValues with Inspectors
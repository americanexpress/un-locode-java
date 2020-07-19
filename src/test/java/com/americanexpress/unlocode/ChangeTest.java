/*
 * Copyright 2020 American Express Travel Related Services Company, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.americanexpress.unlocode;


import org.junit.Assert;
import org.junit.Test;

public class ChangeTest {

  @Test
  public void testChange() {
    Assert.assertEquals(Change.NONE, Change.fromString(""));
    Assert.assertEquals(Change.NEWLY_ADDED, Change.fromString("+"));
    Assert.assertEquals(Change.TO_BE_REMOVED, Change.fromString("X"));
    Assert.assertEquals(Change.TO_BE_CHANGED, Change.fromString("|"));
    Assert.assertEquals(Change.NAME_CHANGED, Change.fromString("#"));
    Assert.assertEquals(Change.REFERENCE_ENTRY, Change.fromString("="));
    Assert.assertEquals(Change.US_LOCATION_WITH_DUPLICATE_IATA_CODE, Change.fromString("!"));
    Assert.assertEquals(Change.NONE, Change.fromString("ZZ"));
  }
}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class LocationTest {

  @Test
  public void testCompare() {
    Location usNyc = Location.builder().country("US").code("NYC").build();
    Location auApwA = Location.builder().country("AU").code("APW").name("AAA").build();
    Location auApwB = Location.builder().country("AU").code("APW").name("BBB").build();
    Location auZsh = Location.builder().country("AU").code("ZSH").build();
    List<Location> locs = new ArrayList<>();
    locs.add(usNyc);
    locs.add(auApwA);
    locs.add(auApwB);
    locs.add(auZsh);
    Collections.sort(locs);
    Assert.assertSame(auApwA, locs.get(0));
    Assert.assertSame(auApwB, locs.get(1));
    Assert.assertSame(auZsh, locs.get(2));
    Assert.assertSame(usNyc, locs.get(3));
  }


}

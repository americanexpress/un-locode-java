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

import com.americanexpress.unlocode.Coordinates.LatitudeDirection;
import com.americanexpress.unlocode.Coordinates.LongitudeDirection;
import org.junit.Assert;
import org.junit.Test;

public class CoordinatesTest {

  @Test
  public void unkown() {
    Assert.assertSame(Coordinates.UNKNOWN, Coordinates.valueOf(""));
  }

  @Test
  public void invalid() {
    Assert.assertSame(Coordinates.INVALID, Coordinates.valueOf("12345Z"));
  }

  @Test
  public void validCoord() {
    String coord = "3500N 03330E";
    Coordinates coordinates = Coordinates.valueOf(coord);
    Assert.assertNotNull(coord);
    Assert.assertEquals(35, coordinates.getLatitudeDegrees());
    Assert.assertEquals(0, coordinates.getLatitudeMinutes());
    Assert.assertSame(LatitudeDirection.NORTH, coordinates.getLatitudeDirection());
    Assert.assertEquals(33, coordinates.getLongitudeDegrees());
    Assert.assertEquals(30, coordinates.getLongitudeMinutes());
    Assert.assertSame(LongitudeDirection.EAST, coordinates.getLongitudeDirection());
  }

  @Test
  public void validCoord13Long() {
    String coord = "13500S 03330W";
    Coordinates coordinates = Coordinates.valueOf(coord);
    Assert.assertNotNull(coord);
    Assert.assertEquals(135, coordinates.getLatitudeDegrees());
    Assert.assertEquals(0, coordinates.getLatitudeMinutes());
    Assert.assertSame(LatitudeDirection.SOUTH, coordinates.getLatitudeDirection());
    Assert.assertEquals(33, coordinates.getLongitudeDegrees());
    Assert.assertEquals(30, coordinates.getLongitudeMinutes());
    Assert.assertSame(LongitudeDirection.WEST, coordinates.getLongitudeDirection());
  }


}

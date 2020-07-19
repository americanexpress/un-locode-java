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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class LocationsTest {

  @Test
  public void getAllLocationIds() {
    Collection<LocationId> allLocationIds = Locations.getAllLocationIds();
    Assert.assertEquals(109656, allLocationIds.size());
  }

  @Test
  public void getAllLocations() {
    Collection<Location> allLocations = Locations.getAllLocations();

    // the number is higher than locationIds because some locations have the same code for slightly different names
    // e.g.:
    // BE,BRU,Brussel (Bruxelles),Brussel (Bruxelles),BRU,AI,1234----,1101,,5050N 00420E,
    // BE,BRU,Bruxelles (Brussel),Bruxelles (Brussel),BRU,AI,1234----,1101,,5050N 00420E,
    Assert.assertEquals(109801, allLocations.size());
  }

  @Test
  public void getDuplicateLocationsById() {
    Collection<Location> brussels = Locations.getLocations("BE", "BRU");
    Assert.assertEquals(brussels.size(), 2);
    List<Location> brusList = new ArrayList<>(brussels);
    Assert.assertEquals(2, brusList.size());
  }

  @Test
  public void getLocationById() {
    Collection<Location> hoves = Locations.getLocations("GB", "HEV");
    Assert.assertEquals(hoves.size(), 1);
  }

  @Test
  public void getLocationIdsFromName() {
    Collection<LocationId> hoves = Locations.getLocationIdsFromName("Hove");
    List hovesList = new ArrayList<>(hoves);
    Collections.sort(hovesList);
    Assert.assertEquals(2, hovesList.size());
    Assert.assertEquals(new LocationId("BE", "HOV"), hovesList.get(0));
    Assert.assertEquals(new LocationId("GB", "HEV"), hovesList.get(1));
  }

  @Test
  public void getLocationIdsFromNameWithoutDiatrics() {
    Collection<LocationId> ascensions = Locations.getLocationIdsFromNameWithoutDiatrics("Ascension");
    List ascensionsList = new ArrayList<>(ascensions);
    Collections.sort(ascensionsList);
    Assert.assertEquals(2, ascensionsList.size());
    Assert.assertEquals(new LocationId("BO", "ASC"), ascensionsList.get(0));
    Assert.assertEquals(new LocationId("SH", "ASC"), ascensionsList.get(1));
  }

  @Test
  public void findLocationIdsFromNameStartingWith() {
    Collection<LocationId> ascensions = Locations.findLocationIdsFromNameStartingWith("Greenbrier");
    List ascensionsList = new ArrayList<>(ascensions);
    Collections.sort(ascensionsList);
    Assert.assertEquals(2, ascensionsList.size());
    Assert.assertEquals(new LocationId("US", "GBI"), ascensionsList.get(0));
    Assert.assertEquals(new LocationId("US", "GR8"), ascensionsList.get(1));
  }


  @Test
  public void findLocationIdsFromNameWithoutDiatricsStartingWith() {
    Collection<LocationId> k = Locations.findLocationIdsFromNameWithoutDiatricsStartingWith("korc");
    List<LocationId> list = new ArrayList<>(k);
    Assert.assertEquals(4, k.size());
    Assert.assertEquals(new LocationId("AL", "KRC"), list.get(0));
  }

  @Test
  public void testExampleInReadme() {
    long airportBorderOutsideFinlandCount = Locations.getAllLocations().stream().filter(
        e -> e.getFunctions().isAirport() && e.getFunctions().isBorderCrossing() && !e.getCountry().equals("FI"))
        .count();
    Assert.assertEquals(15,airportBorderOutsideFinlandCount);
  }

}

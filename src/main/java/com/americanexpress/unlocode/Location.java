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

import java.time.YearMonth;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PACKAGE)
@Getter
@ToString
public class Location implements Comparable {

  private final String country;
  private final String code;
  private final String name;
  private final String nameWithoutDiatrics;
  private final String subdivision;
  private final Status status;
  private final Functions functions;
  private final Optional<YearMonth> date;
  private final String iata;
  private final Coordinates coordinates;
  private final String remarks;
  private final Change change;

  public LocationId getLocationId() {
    return new LocationId(country, code);
  }

  @Override
  public int compareTo(Object o) {
    Location other = (Location) o;
    int idCompare = getLocationId().compareTo(other.getLocationId());
    if (idCompare == 0) {
      return this.toString().compareTo(other.toString());
    } else {
      return idCompare;
    }
  }
}

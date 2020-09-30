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

import java.util.Objects;
import lombok.Getter;

@Getter
public class Coordinates {

  public static final Coordinates UNKNOWN = new Coordinates();
  public static final Coordinates INVALID = new Coordinates();

  private int latitudeDegrees;
  private int latitudeMinutes;
  private LatitudeDirection latitudeDirection;
  private int longitudeDegrees;
  private int longitudeMinutes;
  private LongitudeDirection longitudeDirection;

  private Coordinates() {
  }

  private Coordinates(int latitudeDegrees, int latitudeMinutes,
      LatitudeDirection latitudeDirection, int longitudeDegrees, int longitudeMinutes,
      LongitudeDirection longitudeDirection) {
    this.latitudeDegrees = latitudeDegrees;
    this.latitudeMinutes = latitudeMinutes;
    this.latitudeDirection = latitudeDirection;
    this.longitudeDegrees = longitudeDegrees;
    this.longitudeMinutes = longitudeMinutes;
    this.longitudeDirection = longitudeDirection;
  }

  public static Coordinates valueOf(String input) {
    Objects.requireNonNull(input);
    if (input.isEmpty()) {
      return UNKNOWN;
    }
    if (input.length() != 12 && input.length() != 13) {
      return INVALID;
    }
    String[] array = input.split(" ");
    String latitude = array[0];

    // the two last digits refer to minutes and the two or three first digits indicate the degrees.
    int latitudeDegrees = Integer.parseInt(latitude.substring(0, latitude.length() - 3));
    int latitudeMinutes = Integer.parseInt(latitude.substring(latitude.length() - 3, latitude.length() - 1));
    LatitudeDirection latitudeDirection = latitude.charAt(latitude.length() - 1) == 'N' ?
        LatitudeDirection.NORTH : LatitudeDirection.SOUTH;

    String longitude = array[1];
    int longitudeDegrees = Integer.parseInt(longitude.substring(0, longitude.length() - 3));
    int longitudeMinutes = Integer.parseInt(longitude.substring(longitude.length() - 3, longitude.length() - 1));
    LongitudeDirection longitudeDirection =
        longitude.charAt(longitude.length() - 1) == 'W' ? LongitudeDirection.WEST : LongitudeDirection.EAST;

    return new Coordinates(latitudeDegrees, latitudeMinutes, latitudeDirection,
        longitudeDegrees, longitudeMinutes, longitudeDirection);
  }

  @Override
  public String toString() {
    if (this == UNKNOWN) {
      return "UNKNOWN";
    }
    if (this == INVALID) {
      return "INVALID";
    }
    return
        latitudeDegrees + "°"
            + latitudeMinutes + "′"
            + latitudeDirection.toString().charAt(0) + " "
            + longitudeDegrees + "°" +
            +longitudeMinutes + "′"
            + longitudeDirection.toString().charAt(0);
  }

  public enum LatitudeDirection {NORTH, SOUTH}

  public enum LongitudeDirection {EAST, WEST}

}

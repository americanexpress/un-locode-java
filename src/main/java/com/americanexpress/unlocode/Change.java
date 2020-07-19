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

public enum Change {
  //+ (newly added); X (to be removed); | (changed); # (name changed); = (reference entry); ! (US location with duplicate IATA code)
  NONE(""),
  NEWLY_ADDED("+"),
  TO_BE_REMOVED("X"),
  TO_BE_CHANGED("|"),
  NAME_CHANGED("#"),
  REFERENCE_ENTRY("="),
  US_LOCATION_WITH_DUPLICATE_IATA_CODE("!");

  private final String code;

  Change(String code) {
    this.code = code;
  }

  public static Change fromString(String input) {
    switch (input) {
      case "+":
        return NEWLY_ADDED;
      case "X":
        return TO_BE_REMOVED;
      case "|":
        return TO_BE_CHANGED;
      case "#":
        return NAME_CHANGED;
      case "=":
        return REFERENCE_ENTRY;
      case "!":
        return US_LOCATION_WITH_DUPLICATE_IATA_CODE;
      default:
        return NONE;
    }
  }

}

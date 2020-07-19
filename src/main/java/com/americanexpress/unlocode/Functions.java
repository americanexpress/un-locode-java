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
import lombok.ToString;

@Getter
@ToString
public class Functions {

  public static final Functions UNKNOWN = new Functions();
  private static final String UNKOWN_STRING = "0-------";

  /*
      1	Port, as defined in Rec 16
      2	Rail Terminal
      3	Road Terminal
      4	Airport
      5	Postal Exchange Office
      6	Multimodal Functions (ICDs, etc.)
      7	Fixed Transport Functions (e.g. Oil platform)
      8	Inland Port
      B	Border Crossing
     */
  private boolean port;
  private boolean railTerminal;
  private boolean roadTerminal;
  private boolean airport;
  private boolean postalExchangeOffice;
  private boolean multimodalFunctions;
  private boolean fixedTransportFunctions;
  private boolean borderCrossing;

  private Functions() {
  }

  private Functions(boolean port, boolean railTerminal, boolean roadTerminal, boolean airport,
      boolean postalExchangeOffice, boolean multimodalFunctions, boolean fixedTransportFunctions,
      boolean borderCrossing) {
    this.port = port;
    this.railTerminal = railTerminal;
    this.roadTerminal = roadTerminal;
    this.airport = airport;
    this.postalExchangeOffice = postalExchangeOffice;
    this.multimodalFunctions = multimodalFunctions;
    this.fixedTransportFunctions = fixedTransportFunctions;
    this.borderCrossing = borderCrossing;
  }

  public static Functions valueOf(String input) {
    Objects.requireNonNull(input);
    if (input.length() != 8) {
      throw new IllegalArgumentException("Function must contain 8 characters [" + input + "]");
    }
    if (UNKOWN_STRING.equals(input)) {
      return UNKNOWN;
    }

    boolean port = parse(input, 0, '1');
    boolean railTerminal = parse(input, 1, '2');
    boolean roadTerminal = parse(input, 2, '3');
    boolean airport = parse(input, 3, '4');
    boolean postalExchangeOffice = parse(input, 4, '5');
    boolean multimodalFunctions = parse(input, 5, '6');
    boolean fixedTransportFunctions = parse(input, 6, '7');
    boolean borderCrossing = parse(input, 7, 'B');
    return new Functions(port, railTerminal, roadTerminal, airport, postalExchangeOffice, multimodalFunctions,
        fixedTransportFunctions, borderCrossing);
  }

  private static boolean parse(String input, int i, char name) {
    char c = input.charAt(i);
    if (c == '-') {
      return false;
    }
    if (c == name) {
      return true;
    }
    throw new IllegalArgumentException("Expected either '-' or '" + name + "' but got '" + c + "'");
  }

}

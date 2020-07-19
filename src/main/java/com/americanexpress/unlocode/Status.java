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

import lombok.Getter;

@Getter
public enum Status {

  BLANK(""),
  AA("Approved by competent national government agency"),
  AC("Approved by Customs Authority"),
  AF("Approved by national facilitation body"),
  AI("Code adopted by international  organisation (IATA, ECLAC, EUROSTAT, etc.)"),
  AM("Approved by the UN/LOCODE Maintenance Agency"),
  AQ("Entry approved, functions not verified"),
  AS("Approved by national standardisation body"),
  QQ("Original entry not verified since date indicated"),
  RL("Recognised location - Existence and representation of location name confirmed by check against "
      + "nominated gazetteer or other reference work"),
  RN("Request from credible national sources for locations in their own country"),
  RQ("Request under consideration"),
  RR("Request rejected"),
  UR("Entry included on user's request; not officially approved"),
  XX("Entry that will be removed from the next issue of UN/LOCODE");


  private final String description;

  Status(String description) {
    this.description = description;
  }
}

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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class Locations {

  private static final Multimap<LocationId, Location> locationIdToLocationMap = HashMultimap.create();
  private static final Multimap<String, LocationId> nameToLocationIdMap = HashMultimap.create();
  private static final Multimap<String, LocationId> nameWithoutDiatricsToLocationIdMap = HashMultimap.create();
  private static final List<String> sortedNames;
  private static final List<String> sortedNamesWithoutDiatrics;

  static {
    InputStream stream = Locations.class.getResourceAsStream("/unlocode/un-locode.csv");
    Reader in = new InputStreamReader(stream);
    Iterable<CSVRecord> records;
    try {
      records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    } catch (IOException ioe) {
      throw new IllegalStateException("Unable to parse /unlocode/un-locode.csv");
    }
    SortedSet<String> namesSet = new TreeSet<>();
    SortedSet<String> namesWithoutDiatricsSet = new TreeSet<>();
    for (CSVRecord record : records) {
      Location location = Location.builder()
          .change(Change.fromString(record.get("Change")))
          .country(record.get("Country"))
          .code(record.get("Location"))
          .name(record.get("Name"))
          .nameWithoutDiatrics(record.get("NameWoDiacritics"))
          .subdivision(record.get("Subdivision"))
          .status(record.get("Status").isEmpty() ? Status.BLANK : Status.valueOf(record.get("Status")))
          .functions(Functions.valueOf(record.get("Function")))
          .date(parseYearMonth(record.get("Date")))
          .iata(record.get("IATA"))
          .coordinates(Coordinates.valueOf(record.get("Coordinates")))
          .remarks(record.get("Remarks"))
          .build();

      LocationId locationId = location.getLocationId();
      locationIdToLocationMap.put(locationId, location);
      nameToLocationIdMap.put(location.getName().toLowerCase(Locale.ENGLISH), locationId);
      nameWithoutDiatricsToLocationIdMap.put(location.getNameWithoutDiatrics().toLowerCase(Locale.ENGLISH), locationId);
      namesSet.add(location.getName().toLowerCase(Locale.ENGLISH));
      namesWithoutDiatricsSet.add(location.getNameWithoutDiatrics().toLowerCase(Locale.ENGLISH));
    }
    sortedNames = new ArrayList<>(namesSet);
    Collections.sort(sortedNames);
    sortedNamesWithoutDiatrics = new ArrayList<>(namesWithoutDiatricsSet);
    Collections.sort(sortedNamesWithoutDiatrics);
  }

  private Locations() {
  }

  private static Optional<YearMonth> parseYearMonth(String date) {
    if (date == null || date.isEmpty()) {
      return Optional.empty();
    }
    int year;
    if (date.startsWith("9")) {
      year = 1900 + Integer.parseInt(date.substring(0, 2));
    } else {
      year = 2000 + Integer.parseInt(date.substring(0, 2));
    }
    return Optional.of(YearMonth.of(year, Integer.parseInt(date.substring(2, 4))));
  }

  /**
   * Returns all of the LocationIds.
   *
   * @return all LocationIds.
   */
  public static Collection<LocationId> getAllLocationIds() {
    return locationIdToLocationMap.keySet();
  }

  /**
   * Returns all of the Locations.
   *
   * @return all Locations.
   */
  public static Collection<Location> getAllLocations() {
    return new HashSet<>(locationIdToLocationMap.values());
  }

  /**
   * Returns all of the Locations for the given country and code.
   * Note there may be several entries corresponding to a single country and code; for example, in multi-lingual
   * countries: <pre>BE,BRU</pre> maps to "Brussel (Bruxelles)" and "Bruxelles (Brussel)".
   *
   * @param country the ISO-3166 alpha-2 country code.
   * @param code the location code
   *
   * @return all the Locations matching the given country and code.
   */
  public static Collection<Location> getLocations(String country, String code) {
    return getLocations(new LocationId(country, code));
  }

  /**
   * Returns all of the Locations for the given LocationId (country and code).
   *
   * Note there may be several entries corresponding to a single country and code; for example, in multi-lingual
   * countries: <pre>BE,BRU</pre> maps to "Brussel (Bruxelles)" and "Bruxelles (Brussel)".
   *
   * @param locationId the id (made of country code and location code) for the location to retrieve
   *
   * @return all the Locations matching the given LocationId.
   */
  public static Collection<Location> getLocations(LocationId locationId) {
    return locationIdToLocationMap.get(locationId);
  }

  /**
   * Returns all of the Locations matching the name; comparisons are case-insensitive.
   *
   * @param name the name to find the LocationIds for
   *
   * @return all the Locations matching the given name.
   */
  public static Collection<LocationId> getLocationIdsFromName(String name) {
    return nameToLocationIdMap.get(name.toLowerCase(Locale.ENGLISH));
  }

  /**
   * Returns all of the Locations matching the "name without diatrics"; comparisons are case-insensitive.
   * If any diatrics are present in the name passed in parameter, no result will be returned.
   *
   * @param nameWitoutDiatrics the name to find the LocationIds for, to find matches, this must not contain any diatric
   *
   * @return all the Locations matching the given name.
   */
  public static Collection<LocationId> getLocationIdsFromNameWithoutDiatrics(
      String nameWitoutDiatrics) {
    return nameWithoutDiatricsToLocationIdMap.get(nameWitoutDiatrics.toLowerCase(Locale.ENGLISH));
  }

  /**
   * Returns all of the Locations starting with the name in parameter; the search is case-insensitive.
   *
   * @param start the start of a name to look the locationIds for.
   *
   * @return the LocationIds whose name starts with the parameter.
   */
  public static List<LocationId> findLocationIdsFromNameStartingWith(String start) {
    return findLocationIds(start, sortedNames, nameToLocationIdMap);
  }

  /**
   * Returns all of the Locations starting with the name in parameter; the search is case-insensitive.
   * If any diatrics are present in the string passed in parameter, no result will be returned.
   *
   * @param start the start of a name without diatrics to look the locationIds for.
   *
   * @return the LocationIds whose name without diatrics starts with the parameter.
   */
  public static List<LocationId> findLocationIdsFromNameWithoutDiatricsStartingWith(String start) {
    return findLocationIds(start, sortedNamesWithoutDiatrics, nameWithoutDiatricsToLocationIdMap);
  }

  private static List<LocationId> findLocationIds(String start, List<String> sortedList,
      Multimap<String, LocationId> map) {
    List<String> foundStrings = findInSortedListStartingWith(sortedList, start.toLowerCase(Locale.ENGLISH));
    List<Collection<LocationId>> list = foundStrings.stream()
        .map(map::get)
        .collect(Collectors.toList());
    return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  private static List<String> findInSortedListStartingWith(List<String> list, String start) {
    List<String> foundElements = new ArrayList<>();
    int i = Collections.binarySearch(list, start);
    if (i < 0) {
      i = -(i + 1);
    }
    while (i < list.size() && list.get(i).startsWith(start)) {
      foundElements.add(list.get(i));
      i++;
    }
    return foundElements;
  }

}
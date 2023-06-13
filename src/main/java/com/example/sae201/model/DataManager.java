package com.example.sae201.model;

import com.example.sae201.Main;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * The DataManager class handles the management of seismic data.
 */
public class DataManager {
    private final String CSV_FILE_NAME_DEFAULT = "SisFrance_seismes_20230604151458.csv";
    private URL csv_file_path_import;
    private final String CSV_DEP_FILE_NAME = "FrenchDepartments.csv";
    private final String GEOJSON_DEP_FILE_NAME = "DepartmentData.geojson";
    private MapLocationChecker mapLocationChecker;
    private HashMap<String, String> frenchDepartments;
    private ObservableList<Data> dataList;
    private ObservableList<Data> filteredData;
    private ObservableMap<String, Object> searchData;
    private BooleanProperty searchDataUpdated;
    private BooleanProperty dataFiltered;
    private BooleanBinding searchAndFilterBinding;
    private ObservableList<YearIntensityData> intensityPerYearData;
    private Map<String, Integer> intensityData;

    /**
     * Constructs a DataManager object.
     */
    public DataManager() {
        searchDataUpdated = new SimpleBooleanProperty(false);
        dataFiltered = new SimpleBooleanProperty(false);
        searchAndFilterBinding = searchDataUpdated.and(dataFiltered);
        dataList = FXCollections.observableArrayList();
        frenchDepartments = new HashMap<>();
        filteredData = FXCollections.observableArrayList();
        searchData = FXCollections.observableHashMap();
        intensityPerYearData = FXCollections.observableArrayList();
        intensityData = new HashMap<>();
        try {
            mapLocationChecker = new MapLocationChecker(GEOJSON_DEP_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads seismic data from a CSV file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void loadData() throws IOException {
        dataList.clear();
        URL csvFileUrl = Main.class.getResource(CSV_FILE_NAME_DEFAULT);
        if (csv_file_path_import != null) {
            csvFileUrl = csv_file_path_import;
        }
        if (csvFileUrl == null) {
            throw new IOException("File not found: " + CSV_FILE_NAME_DEFAULT);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFileUrl.openStream()))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmpData = parseLineCSV(line);

                Data data = new Data(Integer.parseInt(tmpData[0]));
                data.setDate(tmpData[1]);
                data.setTime(tmpData[2]);
                data.setName(tmpData[3]);
                data.setRegion(tmpData[4]);
                data.setShock(tmpData[5]);
                data.setXRGF93(parseDoubleOrNull(tmpData[6]));
                data.setYRGF93(parseDoubleOrNull(tmpData[7]));
                data.setLatitude(parseDoubleOrNull(tmpData[8]));
                data.setLongitude(parseDoubleOrNull(tmpData[9]));
                data.setIntensity(parseDoubleOrNull(tmpData[10]));
                data.setIntensityQuality(tmpData[11]);

                dataList.add(data);
            }
        }
    }

    /**
     * Loads French departments data from a CSV file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void loadDepData() throws IOException {
        URL csvFileUrl = Main.class.getResource(CSV_DEP_FILE_NAME);
        if (csvFileUrl == null) {
            throw new IOException("File not found: " + CSV_DEP_FILE_NAME);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFileUrl.openStream()))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmpData = parseLineCSV(line);

                frenchDepartments.put(tmpData[0], tmpData[1]);
            }
        }
    }

    /**
     * Parses a line of CSV data.
     *
     * @param line the line of CSV data
     * @return an array of parsed fields
     */
    private String[] parseLineCSV(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder fieldBuilder = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                fields.add(fieldBuilder.toString().trim());
                fieldBuilder.setLength(0);
            } else {
                fieldBuilder.append(c);
            }
        }

        fields.add(fieldBuilder.toString().trim());

        return fields.toArray(new String[0]);
    }

    /**
     * Parses a double value from a string, or returns null if the string is empty.
     *
     * @param value the string value to parse
     * @return the parsed double value, or null if the string is empty
     */
    private Double parseDoubleOrNull(String value) {
        if (value != null && !value.isEmpty()) {
            return Double.parseDouble(value);
        }
        return null;
    }

    /**
     * Retrieves the list of seismic data.
     *
     * @return the list of seismic data
     */
    public ObservableList<Data> getDataList() {
        return dataList;
    }

    /**
     * Retrieves the French departments map.
     *
     * @return the French departments map
     */
    public HashMap<String, String> getFrenchDepartments() {
        return frenchDepartments;
    }

    /**
     * Filters the seismic data based on the specified search criteria.
     *
     * @param searchData the search criteria
     * @return the filtered seismic data
     */
    public ObservableList<Data> filterData(ObservableMap<String, Object> searchData) {
        filteredData.clear();
        this.searchData = searchData;

        for (Data data : dataList) {
            System.out.println("\n" + data.toString());
            boolean matchesCriteria = true;

            String dateMin = (String) searchData.get("dateMin");
            String dateMax = (String) searchData.get("dateMax");
            if (dateMin != null && !dateMin.isEmpty() && compareDates(data.getDate(), dateMin) < 0) {
                matchesCriteria = false;
                System.out.println("Date Min: Not Passed!");
            }
            if (dateMax != null && !dateMax.isEmpty() && compareDates(data.getDate(), dateMax) > 0) {
                matchesCriteria = false;
                System.out.println("Date Max: Not Passed!");
            }

            Double intensityMin = (Double) searchData.get("intensityMin");
            Double intensityMax = (Double) searchData.get("intensityMax");
            if (intensityMin != null && data.getIntensity() < intensityMin) {
                matchesCriteria = false;
                System.out.println("Intensity Min: Not Passed");
            }
            if (intensityMax != null && data.getIntensity() > intensityMax) {
                matchesCriteria = false;
                System.out.println("Intensity Max: Not Passed");
            }

            Double dataLatitude = data.getLatitude();
            Double dataLongitude = data.getLongitude();
            String departmentCode = frenchDepartments.get((String) searchData.get("department"));
            boolean isInDepartment = false;
            if (dataLatitude != null && dataLongitude != null) {
                isInDepartment = mapLocationChecker.isInDepartment(dataLatitude, dataLongitude, departmentCode);
            }
            if (departmentCode != null && !isInDepartment) {
                matchesCriteria = false;
                System.out.println("Department: Not Passed");
            }

            if (matchesCriteria) {
                filteredData.add(data);
                System.out.println("\u001B[32m Data Matches: Ok! \u001B[0m");
            } else {
                System.out.println("\u001B[31m Data does not match: Not Ok! \u001B[0m");
            }
        }
        dataFiltered.set(true);
        return filteredData;
    }

    /**
     * Retrieves the filtered seismic data.
     *
     * @return the filtered seismic data
     */
    public ObservableList<Data> getFilteredData() {
        return filteredData;
    }

    /**
     * Retrieves the search criteria.
     *
     * @return the search criteria
     */
    public ObservableMap<String, Object> getSearchData() {
        return searchData;
    }

    /**
     * Compares two dates. "AAAA/MM/JJ" or "AAAA/MM" or "AAAA"
     *
     * @param dataDate   the data date
     * @param filterDate the filter date
     * @return an integer representing the comparison result
     */
    private int compareDates(String dataDate, String filterDate) {
        int minLength = Math.min(dataDate.length(), filterDate.length());
        return dataDate.substring(0, minLength).compareTo(filterDate.substring(0, minLength));
    }

    /**
     * Retrieves all regions in the seismic data.
     *
     * @return the list of regions
     */
    public ObservableList<String> getAllRegions() {
        ObservableList<String> regions = FXCollections.observableArrayList();
        for (Data data : dataList) {
            String region = data.getRegion();
            if (region != null && !region.isEmpty() && !regions.contains(region)) {
                regions.add(region);
            }
        }
        return regions;
    }

    /**
     * Retrieves the property indicating whether the search data has been updated.
     *
     * @return the search data updated property
     */
    public BooleanProperty searchDataUpdatedProperty() {
        return searchDataUpdated;
    }

    /**
     * Retrieves the property indicating whether the data has been filtered.
     *
     * @return the data filtered property
     */
    public BooleanProperty dataFilteredProperty() {
        return dataFiltered;
    }

    /**
     * Retrieves the property representing the binding of search and filter.
     *
     * @return the search and filter binding property
     */
    public BooleanBinding searchAndFilterBindingProperty() {
        return searchAndFilterBinding;
    }

    /**
     * Retrieves the list of intensity data per year.
     *
     * @return the list of intensity data per year
     */
    public ObservableList<YearIntensityData> getIntensityPerYearData() {
        intensityPerYearData.clear();
        Map<String, YearIntensityData> yearDataMap = new HashMap<>();

        for (Data data : filteredData) {
            String year = null;
            if (data.getDate() != null) {
                year = data.getDate().substring(0, 4);
            }

            if (yearDataMap.containsKey(year)) {
                YearIntensityData yearIntensityData = yearDataMap.get(year);
                yearIntensityData.setCount(yearIntensityData.getCount() + 1);
                yearIntensityData.setIntensitySum(yearIntensityData.getIntensitySum() + data.getIntensity());
            } else {
                YearIntensityData yearIntensityData = new YearIntensityData();
                yearIntensityData.setYear(year);
                yearIntensityData.setCount(1);
                yearIntensityData.setIntensitySum(data.getIntensity());
                yearDataMap.put(year, yearIntensityData);
                intensityPerYearData.add(yearIntensityData);
            }
        }

        return intensityPerYearData;
    }

    /**
     * Retrieves the map of intensity data by range.
     *
     * @return the map of intensity data
     */
    public Map<String, Integer> getRichterIntensityData() {
        intensityData.put("0-2", 0);
        intensityData.put("2-3", 0);
        intensityData.put("3-4", 0);
        intensityData.put("4-5", 0);
        intensityData.put("5-6", 0);
        intensityData.put("6-7", 0);
        intensityData.put("7-8", 0);
        intensityData.put("8+", 0);

        for (Data data : filteredData) {
            Double intensity = data.getIntensity();
            String range = getIntensityRange(intensity);

            if (intensityData.containsKey(range)) {
                intensityData.put(range, intensityData.get(range)+1);
            }
        }

        return intensityData;
    }

    /**
     * Retrieves the intensity range for the given intensity value.
     *
     * @param intensity the intensity value
     * @return the intensity range
     */
    private String getIntensityRange(Double intensity) {
        if (intensity >= 0 && intensity < 2) {
            return "0-2";
        } else if (intensity >= 2 && intensity < 3) {
            return "2-3";
        } else if (intensity >= 3 && intensity < 4) {
            return "3-4";
        } else if (intensity >= 4 && intensity < 5) {
            return "4-5";
        } else if (intensity >= 5 && intensity < 6) {
            return "5-6";
        } else if (intensity >= 6 && intensity < 7) {
            return "6-7";
        } else if (intensity >= 7 && intensity < 8) {
            return "7-8";
        } else {
            return "8+";
        }
    }

    /**
     * Imports seismic data from a CSV file at the specified URL.
     *
     * @param url the URL of the CSV file
     */
    public void importCSV(URL url) {
        this.csv_file_path_import = url;
    }
}

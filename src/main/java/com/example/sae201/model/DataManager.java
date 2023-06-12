package com.example.sae201.model;

import com.example.sae201.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class DataManager {
    private final String CSV_FILE_NAME = "SisFrance_seismes_20230604151458.csv";
    private final String CSV_DEP_FILE_NAME = "FrenchDepartments.csv";
    private final String GEOJSON_DEP_FILE_NAME = "DepartmentData.geojson";
    private ObservableList<Data> dataList;
    private HashMap<String, String> frenchDepartments;
    private MapLocationChecker mapLocationChecker;

    public DataManager() {
        dataList = FXCollections.observableArrayList();
        frenchDepartments = new HashMap<>();
        try {
            mapLocationChecker = new MapLocationChecker(GEOJSON_DEP_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData() throws IOException {
        URL csvFileUrl = Main.class.getResource(CSV_FILE_NAME);
        if (csvFileUrl == null) {
            throw new IOException("File not found: " + CSV_FILE_NAME);
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

    private Double parseDoubleOrNull(String value) {
        if (value != null && !value.isEmpty()) {
            return Double.parseDouble(value);
        }
        return null;
    }

    public ObservableList<Data> getDataList() {
        return dataList;
    }

    public HashMap<String, String> getFrenchDepartments() {
        return frenchDepartments;
    }

    public ObservableList<Data> filterData(Map<String, Object> searchData) {
        ObservableList<Data> filteredData = FXCollections.observableArrayList();

        for (Data data : dataList) {
            System.out.println("\n" + data.toString());
            boolean matchesCriteria = true;

            String dateMin = (String) searchData.get("dateMin");
            String dateMax = (String) searchData.get("dateMax");
            if (!dateMin.isEmpty() && compareDates(data.getDate(), dateMin) < 0) {
                matchesCriteria = false;
                System.out.println("Date Min: Not Passed!");
            }
            if (!dateMax.isEmpty() && compareDates(data.getDate(), dateMax) > 0) {
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
        return filteredData;
    }

    private int compareDates(String dataDate, String filterDate) {
        int minLength = Math.min(dataDate.length(), filterDate.length());
        return dataDate.substring(0, minLength).compareTo(filterDate.substring(0, minLength));
    }

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

}

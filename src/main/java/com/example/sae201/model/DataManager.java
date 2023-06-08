package com.example.sae201.model;

import com.example.sae201.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final String CSV_FILE_NAME = "SisFrance_seismes_20230604151458.csv";
    private ObservableList<Data> dataList;

    public DataManager() {
        dataList = FXCollections.observableArrayList();
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
                data.setxRGF93(parseDoubleOrNull(tmpData[6]));
                data.setyRGF93(parseDoubleOrNull(tmpData[7]));
                data.setLatitude(parseDoubleOrNull(tmpData[8]));
                data.setLongitude(parseDoubleOrNull(tmpData[9]));
                data.setIntensity(parseDoubleOrNull(tmpData[10]));
                data.setIntensityQuality(tmpData[11]);

                dataList.add(data);
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
}

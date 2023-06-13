package com.example.sae201.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.sae201.Main;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The MapLocationChecker class is responsible for checking if a given latitude and longitude
 * is inside a specified department's boundaries.
 */
public class MapLocationChecker {
    private JSONObject departmentData;

    /**
     * Constructs a MapLocationChecker object with the specified JSON file path.
     *
     * @param filePath the path to the JSON file containing department data
     * @throws IOException        if an I/O error occurs while reading the file
     * @throws URISyntaxException if the file path is not a valid URI syntax
     */
    public MapLocationChecker(String filePath) throws IOException, URISyntaxException {
        URL resourceUrl = Main.class.getResource(filePath);
        Path path = Paths.get(resourceUrl.toURI());
        String jsonData = new String(Files.readAllBytes(path));
        departmentData = new JSONObject(jsonData);
    }

    /**
     * Checks if the given latitude and longitude fall inside the boundaries of the specified department.
     *
     * @param latitude       the latitude coordinate to check
     * @param longitude      the longitude coordinate to check
     * @param departmentCode the code of the department to check against
     * @return true if the coordinates are inside the department boundaries, false otherwise
     */
    public boolean isInDepartment(double latitude, double longitude, String departmentCode) {
        JSONArray features = departmentData.getJSONArray("features");
        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject geometry = feature.getJSONObject("geometry");
            String code = feature.getJSONObject("properties").getString("code");

            if (code.equals(departmentCode) && isInsideGeometry(latitude, longitude, geometry)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInsideGeometry(double latitude, double longitude, JSONObject geometry) {
        String type = geometry.getString("type");
        if (type.equals("Polygon")) {
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            return isInsidePolygon(latitude, longitude, coordinates);
        } else if (type.equals("MultiPolygon")) {
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            return isInsideMultiPolygon(latitude, longitude, coordinates);
        }
        return false;
    }

    private boolean isInsidePolygon(double latitude, double longitude, JSONArray polygon) {
        for (int i = 0; i < polygon.length(); i++) {
            JSONArray ring = polygon.getJSONArray(i);
            if (isInsideRing(latitude, longitude, ring)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInsideMultiPolygon(double latitude, double longitude, JSONArray multiPolygon) {
        for (int i = 0; i < multiPolygon.length(); i++) {
            JSONArray polygon = multiPolygon.getJSONArray(i);
            if (isInsidePolygon(latitude, longitude, polygon)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInsideRing(double latitude, double longitude, JSONArray ring) {
        int sides = ring.length();
        int j = sides - 1;
        boolean isInside = false;

        for (int i = 0; i < sides; i++) {
            JSONArray point = ring.getJSONArray(i);
            double p1Lat = point.getDouble(1);
            double p1Lon = point.getDouble(0);

            JSONArray prevPoint = ring.getJSONArray(j);
            double p2Lat = prevPoint.getDouble(1);
            double p2Lon = prevPoint.getDouble(0);

            if (((p1Lat > latitude) != (p2Lat > latitude)) &&
                    (longitude < (p2Lon - p1Lon) * (latitude - p1Lat) / (p2Lat - p1Lat) + p1Lon)) {
                isInside = !isInside;
            }
            j = i;
        }
        return isInside;
    }
}

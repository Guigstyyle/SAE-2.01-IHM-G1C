package com.example.sae201.model;


public class Data {
    private int id;
    private String date;
    private String time;
    private String name;
    private String region;
    private String shock;
    private Double xRGF93;
    private Double yRGF93;
    private Double latitude;
    private Double longitude;
    private Double intensity;
    private String intensityQuality;

    public Data(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getShock() {
        return shock;
    }

    public void setShock(String shock) {
        this.shock = shock;
    }

    public Double getXRGF93() {
        return xRGF93;
    }

    public void setXRGF93(Double xRGF93) {
        this.xRGF93 = xRGF93;
    }

    public Double getYRGF93() {
        return yRGF93;
    }

    public void setYRGF93(Double yRGF93) {
        this.yRGF93 = yRGF93;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    public String getIntensityQuality() {
        return intensityQuality;
    }

    public void setIntensityQuality(String intensityQuality) {
        this.intensityQuality = intensityQuality;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", shock='" + shock + '\'' +
                ", xRGF93=" + xRGF93 +
                ", yRGF93=" + yRGF93 +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", intensity=" + intensity +
                ", intensityQuality='" + intensityQuality + '\'' +
                '}';
    }
}

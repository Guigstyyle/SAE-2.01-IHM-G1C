package com.example.sae201.model;

/**
 * The Data class represents a data object.
 */
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

    /**
     * Constructs a Data object with the specified ID.
     *
     * @param id the ID of the data object
     */
    public Data(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the data object.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the date associated with the data object.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for the data object.
     *
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the time associated with the data object.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time for the data object.
     *
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Retrieves the name associated with the data object.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the data object.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the region associated with the data object.
     *
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region for the data object.
     *
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Retrieves the shock associated with the data object.
     *
     * @return the shock
     */
    public String getShock() {
        return shock;
    }

    /**
     * Sets the shock for the data object.
     *
     * @param shock the shock to set
     */
    public void setShock(String shock) {
        this.shock = shock;
    }

    /**
     * Retrieves the X coordinate in RGF93 associated with the data object.
     *
     * @return the X coordinate in RGF93
     */
    public Double getXRGF93() {
        return xRGF93;
    }

    /**
     * Sets the X coordinate in RGF93 for the data object.
     *
     * @param xRGF93 the X coordinate in RGF93 to set
     */
    public void setXRGF93(Double xRGF93) {
        this.xRGF93 = xRGF93;
    }

    /**
     * Retrieves the Y coordinate in RGF93 associated with the data object.
     *
     * @return the Y coordinate in RGF93
     */
    public Double getYRGF93() {
        return yRGF93;
    }

    /**
     * Sets the Y coordinate in RGF93 for the data object.
     *
     * @param yRGF93 the Y coordinate in RGF93 to set
     */
    public void setYRGF93(Double yRGF93) {
        this.yRGF93 = yRGF93;
    }

    /**
     * Retrieves the latitude associated with the data object.
     *
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude for the data object.
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Retrieves the longitude associated with the data object.
     *
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude for the data object.
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Retrieves the intensity associated with the data object.
     *
     * @return the intensity
     */
    public Double getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity for the data object.
     *
     * @param intensity the intensity to set
     */
    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the intensity quality associated with the data object.
     *
     * @return the intensity quality
     */
    public String getIntensityQuality() {
        return intensityQuality;
    }

    /**
     * Sets the intensity quality for the data object.
     *
     * @param intensityQuality the intensity quality to set
     */
    public void setIntensityQuality(String intensityQuality) {
        this.intensityQuality = intensityQuality;
    }

    /**
     * Returns a string representation of the Data object.
     *
     * @return a string representation of the object
     */
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

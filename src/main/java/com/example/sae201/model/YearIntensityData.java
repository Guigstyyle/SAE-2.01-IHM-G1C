package com.example.sae201.model;

import javafx.beans.property.DoubleProperty;

public class YearIntensityData {
    private String year;
    private Integer count;
    private Double intensitySum;

    /**
     * Retrieves the year associated with the data.
     *
     * @return The year.
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year associated with the data.
     *
     * @param year The year to set.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Retrieves the count value.
     *
     * @return The count.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the count value.
     *
     * @param count The count to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Retrieves the sum of intensities.
     *
     * @return The intensity sum.
     */
    public Double getIntensitySum() {
        return intensitySum;
    }

    /**
     * Sets the sum of intensities.
     *
     * @param intensitySum The intensity sum to set.
     */
    public void setIntensitySum(Double intensitySum) {
        this.intensitySum = intensitySum;
    }

    /**
     * Calculates and retrieves the mean intensity value.
     *
     * @return The mean intensity.
     */
    public Double getMean() throws ArithmeticException {
        double tmp = 0;
        if (count != 0) {
            tmp = intensitySum / count;
        }
        return tmp;
    }
}

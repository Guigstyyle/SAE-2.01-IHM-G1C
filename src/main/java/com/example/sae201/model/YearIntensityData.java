package com.example.sae201.model;

public class YearIntensityData {
    private String year;
    private Integer count;
    private Double intensitySum;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getIntensitySum() {
        return intensitySum;
    }

    public void setIntensitySum(Double intensitySum) {
        this.intensitySum = intensitySum;
    }

    public Double getMean() {
        return intensitySum / count;
    }
}

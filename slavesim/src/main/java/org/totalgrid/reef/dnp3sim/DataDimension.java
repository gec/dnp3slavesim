package org.totalgrid.reef.dnp3sim;


public class DataDimension {
    private int statusCount;
    private int analogCount;
    private int counterCount;

    public DataDimension(int statusCount, int analogCount, int counterCount) {
        this.statusCount = statusCount;
        this.analogCount = analogCount;
        this.counterCount = counterCount;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public int getAnalogCount() {
        return analogCount;
    }

    public int getCounterCount() {
        return counterCount;
    }
}

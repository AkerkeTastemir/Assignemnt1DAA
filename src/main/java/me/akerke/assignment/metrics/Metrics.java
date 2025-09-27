package me.akerke.assignment.metrics;

public class Metrics {

    private long comparisons = 0;
    private long timeNs = 0;

    // Getters
    public long getComparisons() {
        return comparisons;
    }

    public long getTimeNs() {
        return timeNs;
    }

    // Setters
    public void setComparisons(long comparisons) {
        this.comparisons = comparisons;
    }

    public void setTimeNs(long timeNs) {
        this.timeNs = timeNs;
    }

    // Increment methods
    public void incrementComparisons() {
        this.comparisons++;
    }

    public void incrementComparisons(long count) {
        this.comparisons += count;
    }

    public void addTime(long deltaNs) {
        this.timeNs += deltaNs;
    }

    // Reset method
    public void reset() {
        this.comparisons = 0;
        this.timeNs = 0;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "comparisons=" + comparisons +
                ", timeNs=" + timeNs +
                '}';
    }

}

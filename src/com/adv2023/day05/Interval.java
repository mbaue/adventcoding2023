package com.adv2023.day05;

public class Interval {

    private long lowerLimit;

    public void setLowerLimit(long lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public void setUpperLimit(long upperLimit) {
        this.upperLimit = upperLimit;
    }

    private long upperLimit;

    public long getLowerLimit() {
        return lowerLimit;
    }

    public long getUpperLimit() {
        return upperLimit;
    }

    public Interval(long lowerLimit, long upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

//    public boolean containsNum(long number) {
//        return number >= this.lowerLimit && number <= this.upperLimit;
//    }

//    public boolean containsInterval(Interval i) {
//        return this.lowerLimit <= i.getLowerLimit() && this.upperLimit >= i.getUpperLimit();
//    }

    @Override
    public java.lang.String toString() {
        return String.format("%,d", lowerLimit) + " - " + String.format("%,d", upperLimit);
    }

    public void shiftBy(long l) {
        this.lowerLimit -= l;
        this.upperLimit -= l;
    }
}
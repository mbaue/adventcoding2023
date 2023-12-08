package com.adv2023.day05;

public class MappingObject {
    long startNum;
    long endNum;
    long shift;

    public MappingObject(long startNum, long endNum, long shift) {
        this.startNum = startNum;
        this.endNum = endNum;
        this.shift = shift;
    }

    public long getShift() {
        return shift;
    }

    public boolean rangeContains(long num) {
        return startNum <= num && endNum >= num;
    }

    @Override
    public String toString() {
        return "MappingObject{" +
                "startNum=" + String.format("%,d", startNum) +
                ", endNum=" + String.format("%,d", endNum) +
                ", shift=" + String.format("%,d", shift) +
                '}';
    }
}
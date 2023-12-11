package com.adv2023.day11;

import java.util.Objects;

public class Galaxy {
    int name;
    int line;
    int col;

    public Galaxy(int name, int line, int col) {
        this.name = name;
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            Galaxy other = (Galaxy) obj;
            return this.line == other.getLine() && this.col == other.getCol();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "name= ***" + name +
                "***  line=" + line +
                ", col=" + col +
                '}';
    }
}
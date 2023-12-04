package com.adv2023.day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2023/day/3
 * --- Day 3: Gear Ratios ---
 * You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water
 * source, but this is as far as he can bring you. You go inside.
 *
 * It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
 *
 * "Aaah!"
 *
 * You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone!
 * The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
 *
 * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one.
 * If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
 *
 * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of
 * numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally,
 * is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 *
 * Here is an example engine schematic:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and
 * 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
 *
 * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers
 * in the engine schematic?
 *
 * Your puzzle answer was 540212.
 */

public class Day03 {

    public static final int LINE_LENGTH = 140;
    public static String l0 = "";
    public static String l1 = "";
    public static String l2 = "";
    public static int result = 0;

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv03.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            l0 = ".".repeat(LINE_LENGTH);
            l1 = l0;
            l2 = l0;

            while ((line = br.readLine()) != null) {
                l0 = l1;
                l1 = l2;
                l2 = line;
                compareLines();
            }
            l0 = l1;
            l1 = l2;
            l2 = ".".repeat(LINE_LENGTH);
            compareLines();

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + result);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void compareLines() {
        System.out.println(l0);
        System.out.println(l1);
        System.out.println(l2);
        int number = 0;
        int numStart = -1;
        int numEnd;
        boolean isNum = false;
        for (int i = 0; i < LINE_LENGTH; i++) {
            if (Character.isDigit(l1.charAt(i))) {
                if (!isNum) numStart = i;
                isNum = true;
                number = number * 10 + Character.digit(l1.charAt(i), 10);
            } else {
                if (isNum) {
                    numEnd = i - 1;
                    System.out.print(number + ", position: " + numStart + ", " + numEnd);
                    if (hasNeighborChar(numStart, numEnd)) {
                        System.out.println(", number valid");
                        result += number;
                    }
                    System.out.println();
                    number = 0;
                }
                isNum = false;
            }
        }
        if (isNum) {
            numEnd = LINE_LENGTH - 1;
            System.out.print(number + ", position: " + numStart + ", " + numEnd);
            if (hasNeighborChar(numStart, numEnd)) {
                System.out.println(", number valid");
                result += number;
            }
            System.out.println();
        }
        System.out.println("----- END OF COMPARISON -----");
        System.out.println("result = " + result);
    }

    public static boolean isChar(char ch) {
        return !Character.isDigit(ch) && ch != '.';
    }

    public static boolean hasNeighborChar(int numStart, int numEnd) {
        // previous line and next line
        for (int i = numStart - 1; i <= numEnd + 1; i++) {
            if (i >= 0 && i < LINE_LENGTH) {
                if (isChar(l0.charAt(i)) || isChar(l2.charAt(i))) {
                    return true;
                }
            }
        }
        // current line
        if (numStart - 1 >= 0) {
            if (isChar(l1.charAt(numStart - 1))) {
                return true;
            }
        }
        if (numEnd + 1 < LINE_LENGTH) {
            return isChar(l1.charAt(numEnd + 1));
        }
        return false;
    }
}
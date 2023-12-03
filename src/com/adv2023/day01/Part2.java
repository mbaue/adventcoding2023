package com.adv2023.day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2023/day/1
 * --- Day 1: Trebuchet?! ---
 * --- Part Two ---
 * Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
 *
 * Equipped with this new information, you now need to find the real first and last digit on each line. For example:
 *
 * two1nine
 * eightwothree
 * abcone2threexyz
 * xtwone3four
 * 4nineeightseven2
 * zoneight234
 * 7pqrstsixteen
 * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
 *
 * What is the sum of all of the calibration values?
 *
 * Your puzzle answer was 54203.
 */

public class Part2 {
    public static void main(String[] args) {
        try {
            File file = new File("resources/adv01.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int result = 0;
            while ((line = br.readLine()) != null) {

                System.out.println(line);
                String lineReplaced = line.replace("oneight", "oneeight").
                        replace("twone", "twoone").
                        replace("eightwo", "eighttwo").
                        replace("eighthree", "eightthree").
                        replace("threeight", "threeeight").
                        replace("sevenine", "sevennine").
                        replace("fiveight", "fiveeight").
                        replace("nineight", "nineeight").
                        replace("one", "1").
                        replace("two", "2").
                        replace("three", "3").
                        replace("four", "4").
                        replace("five", "5").
                        replace("six", "6").
                        replace("seven", "7").
                        replace("eight", "8").
                        replace("nine", "9");
                System.out.println(lineReplaced);
                System.out.println(Day01.getNums(lineReplaced));
                result += Day01.getNums(lineReplaced);
                System.out.println("----------------------------------------");
            }

            System.out.println("------------------------------------------------------------------------");
            System.out.println("result = " + result);
            System.out.println("------------------------------------------------------------------------");

        } catch (IOException e) {
            System.out.println("catch exception: " + e.getMessage());
        }
    }
}

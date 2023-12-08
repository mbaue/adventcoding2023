package com.adv2023.day06;

/**
 * https://adventofcode.com/2023/day/6
 * --- Part Two ---
 * As the race is about to start, you realize the piece of paper with race times and record distances you got earlier
 * actually just has very bad kerning. There's really only one race - ignore the spaces between the numbers on each line.
 *
 * So, the example from before:
 *
 * Time:      7  15   30
 * Distance:  9  40  200
 * ...now instead means this:
 *
 * Time:      71530
 * Distance:  940200
 * Now, you have to figure out how many ways there are to win this single race. In this example, the race lasts for
 * 71530 milliseconds and the record distance you need to beat is 940200 millimeters. You could hold the button
 * anywhere from 14 to 71516 milliseconds and beat the record, a total of 71503 ways!
 *
 * How many ways can you beat the record in this one much longer race?
 *
 * Your puzzle answer was 30565288.
 */
public class Part2 {
    public static void main(String[] args) {

        long time = 45988373L;
        long distance = 295173412781210L;
        long count = 0L;
        for (long i = 0; i <= time; i++) {
            if (i * (time - i) > distance) {
                count++;
            }
        }
        System.out.println("-------------------SOLUTION-------------------------------------");
        System.out.println("my score is = " + count);
        System.out.println("----------------------------------------------------------------");
    }
}

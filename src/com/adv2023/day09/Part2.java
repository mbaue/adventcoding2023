package com.adv2023.day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2023/day/9
 * --- Day 9: Mirage Maintenance ---
 * --- Part Two ---
 * Of course, it would be nice to have even more history included in your report. Surely it's safe to just extrapolate
 * backwards as well, right?
 *
 * For each history, repeat the process of finding differences until the sequence of differences is entirely zero.
 * Then, rather than adding a zero to the end and filling in the next values of each previous sequence, you should
 * instead add a zero to the beginning of your sequence of zeroes, then fill in new first values for each previous
 * sequence.
 *
 * In particular, here is what the third example history looks like when extrapolating back in time:
 *
 * 5  10  13  16  21  30  45
 *   5   3   3   5   9  15
 *    -2   0   2   4   6
 *       2   2   2   2
 *         0   0   0
 * Adding the new values on the left side of each sequence from bottom to top eventually reveals the new left-most
 * history value: 5.
 *
 * Doing this for the remaining example data above results in previous values of -3 for the first history and 0 for
 * the second history. Adding all three new values together produces 2.
 *
 * Analyze your OASIS report again, this time extrapolating the previous value for each history. What is the sum of
 * these extrapolated values?
 *
 * Your puzzle answer was 803.
 */
public class Part2 {

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv09.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int result = 0;

            while ((line = br.readLine()) != null) {
                System.out.println("line=" + line);
                String[] nums = line.split(" ");
                int[] numbers = new int[nums.length];
                int index = 0;
                for (String s : nums) {
                    numbers[index] = Integer.parseInt(s);
                    index++;
                }
                printArr(numbers);
                int x = findFirstNumber(numbers);
                System.out.println("x=" + x);

                int firstNumber =  numbers[0] - x;
                result += firstNumber;
                System.out.println("vysledek =  " + firstNumber);
                System.out.println();

                System.out.println();
                System.out.println();
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + result);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printArr(int[] arr) {
        System.out.print("[");
        for (int i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
    }

    public static int findFirstNumber(int[] arr) {
        int[] ar2 = new int[arr.length - 1];
        for (int i = 0; i < ar2.length; i++) {
            int a = arr[i + 1];
            int b = arr[i];
            ar2[i] = a - b;
        }
        printArr(ar2);
        if (isAllZero(ar2)) {
            return 0;
        } else {
            int x = findFirstNumber(ar2);
            System.out.println("x=" + x);
            return ar2[0] - x;
        }
    }

    public static boolean isAllZero(int[] arr) {
        for (int j : arr) {
            if (j != 0) return false;
        }
        System.out.println("------------");
        return true;
    }
}

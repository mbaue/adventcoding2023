package com.adv2023.day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * https://adventofcode.com/2023/day/3
 * --- Day 3: Gear Ratios ---
 * --- Part Two ---
 * The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump
 * in the closest gondola, finally ready to ascend to the water source.
 *
 * You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has
 * a phone labeled "help", so you pick it up and the engineer answers.
 *
 * Before you can explain the situation, she suggests that you look out the window. There stands the engineer,
 * holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the
 * station. You exit the gondola.
 *
 * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that
 * is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
 *
 * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out
 * which gear needs to be replaced.
 *
 * Consider the same engine schematic again:
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
 * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its
 * gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is
 * not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
 *
 * What is the sum of all of the gear ratios in your engine schematic?
 *
 * Your puzzle answer was 87605697.
 */

public class Part2 {
    public static final int LINE_LENGTH = 140;
    public static String l0 = "";
    public static String l1 = "";
    public static String l2 = "";
    public static int result = 0;
    public static int lineNumber = -1;
    public static Map<Asterisk, List<Integer>> gear = new HashMap<>();

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv03.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            l0 = ".".repeat(LINE_LENGTH);
            l1 = l0;
            l2 = l0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                l0 = l1;
                l1 = l2;
                l2 = line;
                compareLines();
            }
            lineNumber++;
            l0 = l1;
            l1 = l2;
            l2 = ".".repeat(LINE_LENGTH);
            compareLines();

            int prod;
            Iterator<List<Integer>> it = gear.values().iterator();
            while (it.hasNext()) {
                List<Integer> l = it.next();
                if (l.size() == 2) {
                    System.out.println(l.get(0));
                    System.out.println(l.get(1));

                    prod = l.get(0) * l.get(1);
                    System.out.println("soucin je " + prod);
                    result += prod;
                    System.out.println("result je " + result);
                }
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + result);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void compareLines() {
        System.out.println();
        System.out.println("Comparing lines:");
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
                    checkAsterisk(numStart, numEnd, number);
                    number = 0;
                }
                isNum = false;
            }
        }
        if (isNum) {
            numEnd = LINE_LENGTH - 1;
            checkAsterisk(numStart, numEnd, number);
        }
        System.out.println("----- END OF COMPARISON -----");
    }

    public static void checkAsterisk(int start, int end, int number) {
        // previous and next line
        for (int i = start - 1; i <= end + 1; i++) {
            if (i >= 0 && i < LINE_LENGTH) {
                if (l0.charAt(i) == '*') {
                    System.out.println("number=" + number + ", line=" + (lineNumber - 1) + ", position=" + i + " ... asterisk 1");
                    putItIn(lineNumber - 1, i, number);
                }
            }
            if (i >= 0 && i < LINE_LENGTH) {
                if (l2.charAt(i) == '*') {
                    System.out.println("number=" + number + ", line=" + (lineNumber + 1) + ", position=" + i + " ... asterisk 2");
                    putItIn(lineNumber + 1, i, number);
                }
            }
        }
        // current line
        if (start - 1 >= 0) {
            if (l1.charAt(start - 1) == '*') {
                System.out.println("number=" + number + ", line=" + (lineNumber) + ", position=" + (start - 1) + " ... asterisk 3");
                putItIn(lineNumber, start - 1, number);
            }
        }
        if (end + 1 < LINE_LENGTH) {
            if (l1.charAt(end + 1) == '*') {
                System.out.println("number=" + number + ", line=" + (lineNumber) + ", position=" + (end + 1) + " ... asterisk 4");
                putItIn(lineNumber, end + 1, number);
            }
        }
    }

    public static void putItIn(int line, int position, int number) {
        Iterator<Asterisk> iter = gear.keySet().iterator();
        Asterisk ax = new Asterisk(line, position);
        List<Integer> lx = new ArrayList<>();
        lx.add(number);
        while (iter.hasNext()) {
            Asterisk ast = iter.next();
            if (ast.getLine() == line && ast.getPosition() == position) {
                gear.get(ast).add(number);
                return;
            }
        }
        gear.put(ax, lx);
    }
}
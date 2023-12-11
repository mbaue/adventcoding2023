package com.adv2023.day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://adventofcode.com/2023/day/11
 * --- Day 11: Cosmic Expansion ---
 * --- Part Two ---
 * The galaxies are much older (and thus much farther apart) than the researcher initially estimated.
 *
 * Now, instead of the expansion you did before, make each empty row or column one million times larger. That is,
 * each empty row should be replaced with 1000000 empty rows, and each empty column should be replaced with 1000000
 * empty columns.
 *
 * (In the example above, if each empty row or column were merely 10 times larger, the sum of the shortest paths
 * between every pair of galaxies would be 1030. If each empty row or column were merely 100 times larger, the sum of
 * the shortest paths between every pair of galaxies would be 8410. However, your universe will need to expand far
 * beyond these values.)
 *
 * Starting with the same initial image, expand the universe according to these new rules, then find the length of the
 * shortest path between every pair of galaxies. What is the sum of these lengths?
 *
 * Your puzzle answer was 726820169514.
 */
public class Part2 {

    public static Set<Integer> linesDoubled = new HashSet<>();
    public static Set<Integer> columnsDoubled = new HashSet<>();
    public static final int LENGTH = 140;
    public static final int EXPANSION_RATE = 1000000;

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv11.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 0;

            for (int i = 0; i < LENGTH; i++) {
                linesDoubled.add(i);
                columnsDoubled.add(i);
            }

            int galCount = 0;
            List<Galaxy> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < LENGTH; i++) {
                    if (line.charAt(i) == '#') {
                        columnsDoubled.remove(i);
                        linesDoubled.remove(lineNumber);
                        galCount++;
                        Galaxy g = new Galaxy(galCount, lineNumber, i);
                        list.add(g);
                    }
                }
                lineNumber++;
            }

            int galNum = list.size();
            long result = 0;
            for (int i = 0; i < galNum - 1; i++) {
                Galaxy g1 = list.get(i);
                for (int j = i + 1; j < galNum; j++) {
                    System.out.println("------------------------------------");
                    Galaxy g2 = list.get(j);
                    System.out.println(g1);
                    System.out.println(g2);
                    int dist = getDistance(g1, g2);
                    System.out.println("total distance = " + dist);
                    result += dist;
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

    public static int getDoubled(int a, int b, String s) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        int x;
        if (s.equals("lines")) {
            x = (int) linesDoubled.stream().filter(g -> g > min).filter(g -> g < max).count();
        } else {
            x = (int) columnsDoubled.stream().filter(g -> g > min).filter(g -> g < max).count();
        }
        return x * (EXPANSION_RATE - 1);
    }

    public static int getDistance(Galaxy g1, Galaxy g2) {
        int x = Math.abs(g1.getLine() - g2.getLine());
        int y = getDoubled(g1.getLine(), g2.getLine(), "lines");
        int z = Math.abs(g1.getCol() - g2.getCol());
        int w = getDoubled(g1.getCol(), g2.getCol(), "cols");
        System.out.println("dist line " + x);
        System.out.println("dist col " + z);
        System.out.println("added lines " + y);
        System.out.println("added cols " + w);
        return x + y + z + w;
    }
}
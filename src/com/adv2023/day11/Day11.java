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
 * You continue following signs for "Hot Springs" and eventually come across an observatory. The Elf within turns out
 * to be a researcher studying cosmic expansion using the giant telescope here.
 *
 * He doesn't know anything about the missing machine parts; he's only visiting for this research project. However, he
 * confirms that the hot springs are the next-closest area likely to have people; he'll even take you straight there
 * once he's done with today's observation analysis.
 *
 * Maybe you can help him with the analysis to speed things up?
 *
 * The researcher has collected a bunch of data and compiled the data into a single giant image (your puzzle input).
 * The image includes empty space (.) and galaxies (#). For example:
 *
 * ...#......
 * .......#..
 * #.........
 * ..........
 * ......#...
 * .#........
 * .........#
 * ..........
 * .......#..
 * #...#.....
 * The researcher is trying to figure out the sum of the lengths of the shortest path between every pair of galaxies.
 * However, there's a catch: the universe expanded in the time it took the light from those galaxies to reach the observatory.
 *
 * Due to something involving gravitational effects, only some space expands. In fact, the result is that any rows or
 * columns that contain no galaxies should all actually be twice as big.
 *
 * In the above example, three columns and two rows contain no galaxies:
 *
 *    v  v  v
 *  ...#......
 *  .......#..
 *  #.........
 * >..........<
 *  ......#...
 *  .#........
 *  .........#
 * >..........<
 *  .......#..
 *  #...#.....
 *    ^  ^  ^
 * These rows and columns need to be twice as big; the result of cosmic expansion therefore looks like this:
 *
 * ....#........
 * .........#...
 * #............
 * .............
 * .............
 * ........#....
 * .#...........
 * ............#
 * .............
 * .............
 * .........#...
 * #....#.......
 * Equipped with this expanded universe, the shortest path between every pair of galaxies can be found.
 * It can help to assign every galaxy a unique number:
 *
 * ....1........
 * .........2...
 * 3............
 * .............
 * .............
 * ........4....
 * .5...........
 * ............6
 * .............
 * .............
 * .........7...
 * 8....9.......
 * In these 9 galaxies, there are 36 pairs. Only count each pair once; order within the pair doesn't matter. For
 * each pair, find any shortest path between the two galaxies using only steps that move up, down, left, or right
 * exactly one . or # at a time. (The shortest path between two galaxies is allowed to pass through another galaxy.)
 *
 * For example, here is one of the shortest paths between galaxies 5 and 9:
 *
 * ....1........
 * .........2...
 * 3............
 * .............
 * .............
 * ........4....
 * .5...........
 * .##.........6
 * ..##.........
 * ...##........
 * ....##...7...
 * 8....9.......
 * This path has length 9 because it takes a minimum of nine steps to get from galaxy 5 to galaxy 9 (the eight
 * locations marked # plus the step onto galaxy 9 itself). Here are some other example shortest path lengths:
 *
 * Between galaxy 1 and galaxy 7: 15
 * Between galaxy 3 and galaxy 6: 17
 * Between galaxy 8 and galaxy 9: 5
 * In this example, after expanding the universe, the sum of the shortest path between all 36 pairs of galaxies is 374.
 *
 * Expand the universe, then find the length of the shortest path between every pair of galaxies. What is the sum
 * of these lengths?
 *
 * Your puzzle answer was 9623138.
 */
public class Day11 {
    public static Set<Integer> linesDoubled = new HashSet<>();
    public static Set<Integer> columnsDoubled = new HashSet<>();
    public static final int LENGTH = 140;

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv11.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 0;

            // lists of all lines and columns. those with galaxies are to be removed from list
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
            int result = 0;
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
        if (s.equals("lines")) {
            return (int) linesDoubled.stream().filter(g -> g > min).filter(g -> g < max).count();
        } else {
            return (int) columnsDoubled.stream().filter(g -> g > min).filter(g -> g < max).count();
        }
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
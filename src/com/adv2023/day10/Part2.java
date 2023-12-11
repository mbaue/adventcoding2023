package com.adv2023.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * https://adventofcode.com/2023/day/10
 * --- Day 10: Pipe Maze ---
 * --- Part Two ---
 * You quickly reach the farthest point
 * of the loop, but the animal never emerges. Maybe its nest is within the area enclosed by the loop?
 *
 * To determine whether it's even worth taking the time to search for such a nest, you should calculate how many tiles
 * are contained within the loop. For example:
 *
 * ...........
 * .S-------7.
 * .|F-----7|.
 * .||.....||.
 * .||.....||.
 * .|L-7.F-J|.
 * .|..|.|..|.
 * .L--J.L--J.
 * ...........
 * The above
 * loop encloses merely four tiles - the two pairs of . in the southwest and southeast (marked I below). The middle .
 * tiles (marked O below) are not in the loop. Here is the same loop again with those regions marked:
 *
 * ...........
 * .S-------7.
 * .|F-----7|.
 * .||OOOOO||.
 * .||OOOOO||.
 * .|L-7OF-J|.
 * .|II|O|II|.
 * .L--JOL--J.
 * .....O.....
 * In fact,
 * there doesn't even need to be a full tile path to the outside for tiles to count as outside the loop - squeezing
 * between pipes is also allowed! Here, I is still within the loop and O is still outside the loop:
 *
 * ..........
 * .S------7.
 * .|F----7|.
 * .||OOOO||.
 * .||OOOO||.
 * .|L-7F-J|.
 * .|II||II|.
 * .L--JL--J.
 * ..........
 * In both of the
 * above examples, 4 tiles are enclosed by the loop.
 *
 * Here's a larger example:
 *
 * .F----7F7F7F7F-7....
 * .|F--7||||||||FJ....
 * .||.FJ||||||||L7....
 * FJL7L7LJLJ||LJ.L-7..
 * L--J.L7...LJS7F-7L7.
 * ....F-J..F7FJ|L7L7L7
 * ....L7.F7||L7|.L7L7|
 * .....|FJLJ|FJ|F7|.LJ
 * ....FJL-7.||.||||...
 * ....L---J.LJ.LJLJ...
 * The above sketch has many random bits of ground, some of which are in the loop (I) and some of which are outside it
 * (O):
 *
 * OF----7F7F7F7F-7OOOO
 * O|F--7||||||||FJOOOO
 * O||OFJ||||||||L7OOOO
 * FJL7L7LJLJ||LJIL-7OO
 * L--JOL7IIILJS7F-7L7O
 * OOOOF-JIIF7FJ|L7L7L7
 * OOOOL7IF7||L7|IL7L7|
 * OOOOO|FJLJ|FJ|F7|OLJ
 * OOOOFJL-7O||O||||OOO
 * OOOOL---JOLJOLJLJOOO
 * In this larger example, 8 tiles are enclosed by the loop.
 *
 * Any tile that isn't part of the main loop can count as being enclosed by the loop. Here's another example with many
 * bits of junk pipe lying around that aren't connected to the main loop at all:
 *
 * FF7FSF7F7F7F7F7F---7
 * L|LJ||||||||||||F--J
 * FL-7LJLJ||||||LJL-77
 * F--JF--7||LJLJ7F7FJ-
 * L---JF-JLJ.||-FJLJJ7
 * |F|F-JF---7F7-L7L|7|
 * |FFJF7L7F-JF7|JL---7
 * 7-L-JL7||F7|L7F-7F7|
 * L.L7LFJ|||||FJL7||LJ
 * L7JLJL-JLJLJL--JLJ.L
 * Here are just the tiles that are enclosed by the loop marked with I:
 *
 * FF7FSF7F7F7F7F7F---7
 * L|LJ||||||||||||F--J
 * FL-7LJLJ||||||LJL-77
 * F--JF--7||LJLJIF7FJ-
 * L---JF-JLJIIIIFJLJJ7
 * |F|F-JF---7IIIL7L|7|
 * |FFJF7L7F-JF7IIL---7
 * 7-L-JL7||F7|L7F-7F7|
 * L.L7LFJ|||||FJL7||LJ
 * L7JLJL-JLJLJL--JLJ.L
 * In this last example, 10 tiles are enclosed by the loop.
 *
 * Figure out whether you have time to search for the nest by calculating the area within the loop. How many tiles are
 * enclosed by the loop?
 *
 * Your puzzle answer was 451.
 */
public class Part2 {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv10.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            String[] land = new String[140];
            int lineNum = 0;
            int startHoriz = 0;
            int startVerti = 0;
            int lineLength = 0;

            // load into land structure
            while ((line = br.readLine()) != null) {
                lineLength = line.length();
                land[lineNum] = line;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'S') {
                        startHoriz = i;
                        startVerti = lineNum;
                    }
                }
                lineNum++;
            }

            // find starting directions from S position
            char ch;
            int x1 = -1, x2 = -1, y1 = -1, y2 = -1, dir1 = -1, dir2 = -1;
            System.out.println("starting position: " + startHoriz + ", " + startVerti);
            ch = land[startVerti - 1].charAt(startHoriz);
            if (ch == 'F' || ch == '7' || ch == '|') {
                System.out.println("up   : " + ch);
                if (x1 == -1 && y1 == -1) {
                    x1 = startHoriz;
                    y1 = startVerti - 1;
                    dir1 = UP;
                } else {
                    x2 = startHoriz;
                    y2 = startVerti - 1;
                    dir2 = UP;
                }
            }
            ch = land[startVerti].charAt(startHoriz + 1);
            if (ch == 'J' || ch == '7' || ch == '-') {
                System.out.println("right: " + ch);
                if (x1 == -1 && y1 == -1) {
                    x1 = startHoriz + 1;
                    y1 = startVerti;
                    dir1 = RIGHT;
                } else {
                    x2 = startHoriz + 1;
                    y2 = startVerti;
                    dir2 = RIGHT;
                }
            }
            ch = land[startVerti + 1].charAt(startHoriz);
            if (ch == 'J' || ch == 'L' || ch == '|') {
                System.out.println("down : " + ch);
                if (x1 == -1 && y1 == -1) {
                    x1 = startHoriz;
                    y1 = startVerti + 1;
                    dir1 = DOWN;
                } else {
                    x2 = startHoriz;
                    y2 = startVerti + 1;
                    dir2 = DOWN;
                }
            }
            ch = land[startVerti].charAt(startHoriz - 1);
            if (ch == 'F' || ch == 'L' || ch == '-') {
                System.out.println("left : " + ch);
                if (x1 == -1 && y1 == -1) {
                    x1 = startHoriz - 1;
                    y1 = startVerti;
                    dir1 = LEFT;
                } else {
                    x2 = startHoriz - 1;
                    y2 = startVerti;
                    dir2 = LEFT;
                }
            }

            // count first step performed and add points which are part of curve into set
            int step = 1;
            Set<Point> curve = new HashSet<>();
            Point p = new Point(startHoriz, startVerti);
            curve.add(p);
            p = new Point(x1, y1);
            curve.add(p);
            p = new Point(x2, y2);
            curve.add(p);

            // move further steps and add points into set
            while (x1 != x2 || y1 != y2) {
                step++;
                char ch1 = land[y1].charAt(x1);
                char ch2 = land[y2].charAt(x2);
                switch (ch1) {
                    case 'F': {
                        if (dir1 == UP) {
                            dir1 = RIGHT;
                            x1++;
                        } else if (dir1 == LEFT) {
                            dir1 = DOWN;
                            y1++;
                        }
                        break;
                    }
                    case 'L': {
                        if (dir1 == DOWN) {
                            dir1 = RIGHT;
                            x1++;
                        } else if (dir1 == LEFT) {
                            dir1 = UP;
                            y1--;
                        }
                        break;
                    }
                    case '7': {
                        if (dir1 == UP) {
                            dir1 = LEFT;
                            x1--;
                        } else if (dir1 == RIGHT) {
                            dir1 = DOWN;
                            y1++;
                        }
                        break;
                    }
                    case 'J': {
                        if (dir1 == DOWN) {
                            dir1 = LEFT;
                            x1--;
                        } else if (dir1 == RIGHT) {
                            dir1 = UP;
                            y1--;
                        }
                        break;
                    }
                    case '-': {
                        if (dir1 == LEFT) {
                            x1--;
                        } else if (dir1 == RIGHT) {
                            x1++;
                        }
                        break;
                    }
                    case '|': {
                        if (dir1 == UP) {
                            y1--;
                        } else if (dir1 == DOWN) {
                            y1++;
                        }
                        break;
                    }
                }
                switch (ch2) {
                    case 'F': {
                        if (dir2 == UP) {
                            dir2 = RIGHT;
                            x2++;
                        } else if (dir2 == LEFT) {
                            dir2 = DOWN;
                            y2++;
                        }
                        break;
                    }
                    case 'L': {
                        if (dir2 == DOWN) {
                            dir2 = RIGHT;
                            x2++;
                        } else if (dir2 == LEFT) {
                            dir2 = UP;
                            y2--;
                        }
                        break;
                    }
                    case '7': {
                        if (dir2 == UP) {
                            dir2 = LEFT;
                            x2--;
                        } else if (dir2 == RIGHT) {
                            dir2 = DOWN;
                            y2++;
                        }
                        break;
                    }
                    case 'J': {
                        if (dir2 == DOWN) {
                            dir2 = LEFT;
                            x2--;

                        } else if (dir2 == RIGHT) {
                            dir2 = UP;
                            y2--;
                        }
                        break;
                    }
                    case '-': {
                        if (dir2 == LEFT) {
                            x2--;

                        } else if (dir2 == RIGHT) {
                            x2++;
                        }
                        break;
                    }
                    case '|': {
                        if (dir2 == UP) {
                            y2--;
                        } else if (dir2 == DOWN) {
                            y2++;
                        }
                        break;
                    }
                }
                p = new Point(x1, y1);
                curve.add(p);
                p = new Point(x2, y2);
                curve.add(p);
            }
            System.out.println("both directions met in " + step);

            // create new land array only containting the curve
            String[] landCurve = new String[140];
            for (int o = 0; o < land.length; o++) {
                StringBuilder sb = new StringBuilder();
                for (int pr = 0; pr < lineLength; pr++) {
                    Point prt = new Point(pr, o);
                    if (curve.contains(prt)) {
                        System.out.print(land[o].charAt(pr));
                        sb.append(land[o].charAt(pr));
                    } else {
                        System.out.print(".");
                        sb.append(".");
                    }
                }
                landCurve[o] = sb.toString();
                System.out.println();
            }

            // count points inside the curve
            int pointsIn = 0;
            for (int y = 0; y < land.length; y++) {
                for (int x = 0; x < lineLength; x++) {
                    Point pnt = new Point(x, y);
                    if (!curve.contains(pnt)) {
                        int cross;
                        String modifLine = landCurve[y].substring(x + 1).replaceAll("-", "").replaceAll("L7", "|").replaceAll("F7", "").replaceAll("FJ", "|").replaceAll("LJ", "");
                        cross = countCrosses(modifLine);
                        if (cross % 2 != 0) {
                            pointsIn++;
                        }
                    }
                }
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + pointsIn);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int countCrosses(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|') count++;
        }
        return count;
    }
}
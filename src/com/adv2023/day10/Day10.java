package com.adv2023.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2023/day/10
 * --- Day 10: Pipe Maze ---
 * You use the hang glider to ride the hot air from Desert Island all the way up to the floating metal island. This
 * island is surprisingly cold and there definitely aren't any thermals to glide on, so you leave your hang glider
 * behind.
 *
 * You wander around for a while, but you don't find any people or animals. However, you do occasionally find signposts
 * labeled "Hot Springs" pointing in a seemingly consistent direction; maybe you can find someone at the hot springs
 * and ask them where the desert-machine parts are made.
 *
 * The landscape here is alien; even the flowers and trees are made of metal. As you stop to admire some metal grass,
 * you notice something metallic scurry away in your peripheral vision and jump into a big pipe! It didn't look like
 * any animal you've ever seen; if you want a better look, you'll need to get ahead of it.
 *
 * Scanning the area, you discover that the entire field you're standing on is densely packed with pipes; it was hard
 * to tell at first because they're the same metallic silver color as the "ground". You make a quick sketch of all of
 * the
 * surface pipes you can see (your puzzle input).
 *
 * The pipes are arranged in a two-dimensional grid of tiles:
 *
 * | is a vertical pipe connecting north and south.
 * - is a horizontal pipe connecting east and west.
 * L is a 90-degree bend connecting north and east.
 * J is a 90-degree bend connecting north and west.
 * 7 is a 90-degree bend connecting south and west.
 * F is a 90-degree bend connecting south and east.
 * . is ground; there is no pipe in this tile.
 * S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the
 * pipe has.
 * Based on the acoustics of the animal's scurrying, you're confident the pipe that contains the animal is one large,
 * continuous loop.
 *
 * For example, here is a square loop of pipe:
 * .....
 * .F-7.
 * .|.|.
 * .L-J.
 * .....
 * If the animal had entered this loop in the northwest corner, the sketch would instead
 * look like this:
 *
 * .....
 * .S-7.
 * .|.|.
 * .L-J.
 * .....
 * In the above diagram, the S tile is still a 90-degree F bend: you can tell because of
 * how the adjacent pipes connect to it.
 *
 * Unfortunately, there are also many pipes that aren't connected to the loop! This sketch shows the same loop as
 * above:
 *
 * -L|F7
 * 7S-7|
 * L|7||
 * -L-J|
 * L|-JF
 * In the above diagram, you can still figure out which pipes form the main loop: they're
 * the ones connected to S, pipes those pipes connect to, pipes those pipes connect to, and so on. Every pipe in the
 * main loop connects to its two neighbors (including S, which will have exactly two pipes connecting to it, and which
 * is assumed to connect back to those two pipes).
 *
 * Here is a sketch that contains a slightly more complex main loop:
 *
 * ..F7.
 * .FJ|.
 * SJ.L7
 * |F--J
 * LJ...
 * Here's the same example sketch with the extra, non-main-loop pipe tiles also shown:
 *
 * 7-F7-
 * .FJ|7
 * SJLL7
 * |F--J
 * LJ.LJ
 * If you want to get out ahead of the animal, you should find the tile in the loop that
 * is farthest from the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by
 * direct distance. Instead, you need to find the tile that would take the longest number of steps along the loop to
 * reach from the starting point - regardless of which way around the loop the animal went.
 *
 * In the first example with the square loop:
 *
 * .....
 * .S-7.
 * .|.|.
 * .L-J.
 * ..... You can count the distance each tile in the loop is from the starting point like this:
 *
 * .....
 * .012.
 * .1.3.
 * .234.
 * .....
 * In this example, the farthest point from the start is 4 steps away.
 *
 * Here's the more complex loop again:
 *
 * ..F7.
 * .FJ|.
 * SJ.L7
 * |F--J
 * LJ...
 * Here are the distances for each tile on that loop:
 *
 * ..45.
 * .236.
 * 01.78
 * 14567
 * 23...
 * Find the single giant loop starting at S. How many steps along the loop does it take to get from the starting
 * position to the point farthest from the starting position?
 *
 * Your puzzle answer was 6838.
 */
public class Day10 {
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

            // load into land structure
            while ((line = br.readLine()) != null) {
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
            System.out.println(startHoriz + ", " + startVerti);
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

            // count first step performed
            int step = 1;
            System.out.println("in step=" + step);
            System.out.println("    [" + x1 + ", " + y1 + "], " + land[y1].charAt(x1));
            System.out.println("    [" + x2 + ", " + y2 + "], " + land[y2].charAt(x2));

            // move further steps
            while (x1 != x2 || y1 != y2) {
                System.out.println("---------------");
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
                System.out.println("in step: " + step);
                System.out.println("    [" + x1 + ", " + y1 + "], " + land[y1].charAt(x1));
                System.out.println("    [" + x2 + ", " + y2 + "], " + land[y2].charAt(x2));
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + step);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

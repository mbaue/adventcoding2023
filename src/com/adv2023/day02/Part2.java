package com.adv2023.day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2023/day/2
 * --- Day 2: Cube Conundrum ---
 * --- Part Two ---
 * The Elf says they've stopped producing snow because they aren't getting any water! He isn't sure why the water
 * stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!
 *
 * As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number
 * of cubes of each color that could have been in the bag to make the game possible?
 *
 * Again consider the example games from earlier:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even
 * one fewer cube, the game would have been impossible.
 * Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
 * Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
 * Game 4 required at least 14 red, 3 green, and 15 blue cubes.
 * Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
 * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power
 * of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up
 * these five powers produces the sum 2286.
 *
 * For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?
 *
 * Your puzzle answer was 78375.
 */

public class Part2 {

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv02.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int result = 0;

            while ((line = br.readLine()) != null) {
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println(line);
                String[] main = line.trim().split(":");
                int gameNum = Integer.parseInt(main[0].replace("Game", "").trim());
                System.out.println("game=" + gameNum);
                String game = main[1].trim();
                String[] parts = game.split(";");
                int numParts = parts.length;
                int red = 0;
                int blue = 0;
                int green = 0;
                for (int i = 0; i < numParts; i++) {
                    System.out.println(parts[i]);
                    String[] cubes = parts[i].trim().split(",");

                    for (String cubeColor : cubes) {
                        String[] cubeC = cubeColor.trim().split(" ");
                        int cubeColorNum = Integer.parseInt(cubeC[0]);
                        switch (cubeC[1].trim()) {
                            case "blue": {
                                if (cubeColorNum > blue) {
                                    blue = cubeColorNum;
                                }
                                break;
                            }
                            case "green": {
                                if (cubeColorNum > green) {
                                    green = cubeColorNum;
                                }
                                break;
                            }
                            case "red": {
                                if (cubeColorNum > red) {
                                    red = cubeColorNum;
                                }
                            }
                        }
                    }
                }

                result += (red * blue * green);
                System.out.println("result is now " + result);
            }

            System.out.println("------------------------------------------------------------------------");
            System.out.println("result = " + result);
            System.out.println("------------------------------------------------------------------------");

        } catch (IOException e) {
            System.out.println("catch exception: " + e.getMessage());
        }
    }
}
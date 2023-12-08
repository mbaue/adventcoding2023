package com.adv2023.day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * https://adventofcode.com/2023/day/5
 * --- Part Two ---
 * Everyone will starve if you only plant such a small number of seeds. Re-reading the almanac, it looks like the seeds:
 * line actually describes ranges of seed numbers.
 *
 * The values on the initial seeds: line come in pairs. Within each pair, the first value is the start of the range
 * and the second value is the length of the range. So, in the first line of the example above:
 *
 * seeds: 79 14 55 13
 * This line describes two ranges of seed numbers to be planted in the garden. The first range starts with seed number
 * 79 and contains 14 values: 79, 80, ..., 91, 92. The second range starts with seed number 55 and contains 13 values:
 * 55, 56, ..., 66, 67.
 *
 * Now, rather than considering four seed numbers, you need to consider a total of 27 seed numbers.
 *
 * In the above example, the lowest location number can be obtained from seed number 82, which corresponds to soil 84,
 * fertilizer 84, water 84, light 77, temperature 45, humidity 46, and location 46. So, the lowest location number is 46.
 *
 * Consider all of the initial seed numbers listed in the ranges on the first line of the almanac. What is the lowest
 * location number that corresponds to any of the initial seed numbers?
 *
 * Your puzzle answer was 17729182.
 */
public class Part2 {
    public static void main(String[] args) {

        try {
            File file = new File("resources/adv05.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            String s = "seeds";
            long[] seeds = null;
            List<Interval> intervals = new ArrayList<>();
            List<MappingObject> seedSoil = new ArrayList<>();
            List<MappingObject> soilFert = new ArrayList<>();
            List<MappingObject> fertWatr = new ArrayList<>();
            List<MappingObject> watrLigh = new ArrayList<>();
            List<MappingObject> lighTemp = new ArrayList<>();
            List<MappingObject> tempHumi = new ArrayList<>();
            List<MappingObject> humiLoca = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                } else {
                    if (Character.isDigit(line.charAt(0))) {
                        switch (s) {
                            case "seeds": {
                                seeds = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                int l = seeds.length;
                                for (int i = 0; i < l; i += 2) {
                                    Interval inter = new Interval(seeds[i], seeds[i] + seeds[i + 1] - 1);
                                    intervals.add(inter);
                                }
                                break;
                            }
                            case "seed-to-soil": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                seedSoil.add(mo);
                                break;
                            }
                            case "soil-to-fertilizer": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                soilFert.add(mo);
                                break;
                            }
                            case "fertilizer-to-water": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                fertWatr.add(mo);
                                break;
                            }
                            case "water-to-light": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                watrLigh.add(mo);
                                break;
                            }
                            case "light-to-temperature": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                lighTemp.add(mo);
                                break;
                            }
                            case "temperature-to-humidity": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                tempHumi.add(mo);
                                break;
                            }
                            case "humidity-to-location": {
                                long[] ar = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                                MappingObject mo = new MappingObject(ar[1], ar[1] + ar[2] - 1, ar[1] - ar[0]);
                                humiLoca.add(mo);
                                break;
                            }
                        }
                    } else {
                        s = line.replace(" map:", "");
                    }
                }
            }

            List<Interval> soils = convertByMap(intervals, seedSoil);
            List<Interval> ferts = convertByMap(soils, soilFert);
            List<Interval> watrs = convertByMap(ferts, fertWatr);
            List<Interval> lighs = convertByMap(watrs, watrLigh);
            List<Interval> temps = convertByMap(lighs, lighTemp);
            List<Interval> humis = convertByMap(temps, tempHumi);
            List<Interval> locas = convertByMap(humis, humiLoca);
            System.out.println();
            System.out.println(locas);
            long minLoc = Long.MAX_VALUE;
            for (Interval interv : locas) {
                if (interv.getLowerLimit()<minLoc) {
                    minLoc = interv.getLowerLimit();
                }
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + minLoc);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Interval> convertByMap(List<Interval> intervals, List<MappingObject> mos) {
        ListIterator<Interval> iter = intervals.listIterator();
        List<Interval> outputIntervals = new ArrayList<>();
        int x = 0;
        while (iter.hasNext()) {
            x++;
            System.out.println(x + " -------------------------- ");
            Interval interv = iter.next();
            System.out.println("processing: " + interv);
            boolean mapped = false;
            for (MappingObject mo : mos) {
                System.out.println(mo);
                if (interv.getLowerLimit() >= mo.startNum && interv.getLowerLimit() <= mo.endNum) {
                    if (interv.getUpperLimit() > mo.endNum) {
                        System.out.println("horejsek ufiknout");
                        Interval iNew = new Interval(mo.endNum + 1, interv.getUpperLimit());
                        System.out.println("pridavam " + iNew);
                        iter.add(iNew);
                        iter.previous();
                        interv.setUpperLimit(mo.endNum);
                    }
                    System.out.println("cely se vesel");
                    interv.shiftBy(mo.getShift());
                    System.out.println("posunuto " + interv);
                    mapped = true;
                    outputIntervals.add(interv);
                    break;
                } else if (interv.getUpperLimit() <= mo.endNum && interv.getUpperLimit() >= mo.startNum) {
                    System.out.println("dolejsek ufiknout");
                    Interval iNew = new Interval (interv.getLowerLimit(), mo.startNum-1);
                    iter.add(iNew);
                    iter.previous();
                    interv.setLowerLimit(mo.startNum);
                    interv.shiftBy(mo.getShift());
                    mapped = true;
                    outputIntervals.add(interv);
                    break;
                }
            }
            if (!mapped) {
                outputIntervals.add(interv);
                System.out.println("bez konverze");
            }
        }
    return outputIntervals;
    }
}
package com.adv2023.day08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Part2 {

    public static String instructions;
    public static int cycle;
    public static Map<String, String> map = new HashMap<>();
    public static void main(String[] args) {
        try {
            File file = new File("resources/adv08.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            //instructions
            instructions = br.readLine();
            cycle = instructions.length();

            //skip empty line
            br.readLine();

            // fill nodes into map
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" = ");
                String nodeName = parts[0];
                String nodeDirectios = parts[1].replace("(", "").replace(")", "").replace(", ", "");
                map.put(nodeName, nodeDirectios);
            }

            // search for each A-Z combination
            // information from forum: every start "A" position leads to exactly one "Z" position. Which go into pair was learned separately by
            // running part 1 algorithm. Those not creating pair do not finish run in second.
            long a = findZNode("AAA", "ZZZ");
            long b = findZNode("NBA", "HGZ");
            long c = findZNode("TTA", "RFZ");
            long d = findZNode("VVA", "PSZ");
            long e = findZNode("XSA", "TKZ");
            long f = findZNode("MHA", "GJZ");

            // get greatest common multiple for previously found
            long result = lcm(lcm(lcm( lcm(lcm(a, b), c), d), e), f);

            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + result);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static long findZNode(String startNode, String endNode){
        System.out.println("start=" + startNode + ", end=" + endNode);
        String actualNode = startNode;
        int counter = 0;
        while (!actualNode.equals(endNode)) {
            String nextnode;
            if (instructions.charAt(counter % cycle) == 'R') {
                nextnode = map.get(actualNode).substring(3);
            } else {
                nextnode = map.get(actualNode).substring(0, 3);
            }
            actualNode = nextnode;
            counter++;
        }
        System.out.println("rounds to search " + counter / cycle);
        System.out.println(counter);
        return counter;
    }

    public static long gcd(long x, long y) {
        long rest = x % y;
        while (rest != 0) {
            x = y;
            y = rest;
            rest = x % y;
        }
        return y;
    }

    public static long lcm(long x, long y) {
        return x*y/gcd(x, y);
    }
}
package com.adv2023.day08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day08 {

    public static void main(String[] args) {

        try {
            File file = new File("resources/adv08.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String instructions = br.readLine();
            br.readLine(); // empty line
            Map<String, String> map = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" = ");
                String nodeName = parts[0];
                String nodeDirectios = parts[1].replace("(", "").replace(")", "").replace(", ", "");
                map.put(nodeName, nodeDirectios);
            }

            String start = "AAA";
            String finish = "ZZZ";
            String actualNode = start;
            int counter = 0;
            int cycle = instructions.length();
            while (!actualNode.equals(finish)) {
                String nextnode;
                if (instructions.charAt(counter % cycle) == 'R') {
                    System.out.println("R");
                    nextnode = map.get(actualNode).substring(3);
                    System.out.println(nextnode);
                } else {
                    System.out.println("L");
                    nextnode = map.get(actualNode).substring(0, 3);
                    System.out.println(nextnode);
                }
                actualNode = nextnode;
                counter++;
            }

            System.out.println("-------------------SOLUTION-------------------------------------");
                        System.out.println("my score is = " + counter);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

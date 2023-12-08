package com.adv2023.day07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * https://adventofcode.com/2023/day/7
 * --- Part Two ---
 * To make things a little more interesting, the Elf introduces one additional rule. Now, J cards are jokers - wildcards
 * that can act like whatever card would make the hand the strongest type possible.
 *
 * To balance this, J cards are now the weakest individual cards, weaker even than 2. The other cards stay in the same
 * order: A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J.
 *
 * J cards can pretend to be whatever card is best for the purpose of determining hand type; for example, QJJQ2 is now
 * considered four of a kind. However, for the purpose of breaking ties between two hands of the same type, J is always
 * treated as J, not the card it's pretending to be: JKKK2 is weaker than QQQQ2 because J is weaker than Q.
 *
 * Now, the above example goes very differently:
 *
 * 32T3K 765
 * T55J5 684
 * KK677 28
 * KTJJT 220
 * QQQJA 483
 * 32T3K is still the only one pair; it doesn't contain any jokers, so its strength doesn't increase.
 * KK677 is now the only two pair, making it the second-weakest hand.
 * T55J5, KTJJT, and QQQJA are now all four of a kind! T55J5 gets rank 3, QQQJA gets rank 4, and KTJJT gets rank 5.
 * With the new joker rule, the total winnings in this example are 5905.
 *
 * Using the new joker rule, find the rank of every hand in your set. What are the new total winnings?
 *
 * Your puzzle answer was 245461700.
 */
public class Part2 {
    public static void main(String[] args) {

        try {
            File file = new File("resources/adv07.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            List<Hand> handList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split(" ");
                String hand = parts[0].trim();
                int val = Integer.parseInt(parts[1].trim());
                String handAdj = hand.replace("K", "B").
                        replace("Q", "C").
                        replace("J", "Z").
                        replace("T", "E").
                        replace("9", "F").
                        replace("8", "G").
                        replace("7", "H").
                        replace("6", "I").
                        replace("5", "J").
                        replace("4", "K").
                        replace("3", "L").
                        replace("2", "M");
                Hand h = new Hand(hand, val, handAdj);
                handList.add(h);
            }
            handList.sort(Comparator.naturalOrder());
            int handListSize = handList.size();
            int result = 0;
            for(Hand h : handList) {
                result += h.getBid() * (handListSize);
                handListSize--;
            }
            System.out.println("-------------------SOLUTION-------------------------------------");
            System.out.println("my score is = " + result);
            System.out.println("----------------------------------------------------------------");
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

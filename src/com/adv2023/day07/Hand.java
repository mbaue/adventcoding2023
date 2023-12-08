package com.adv2023.day07;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand> {
    private final String cards;
    private final int bid;
    private final String value;
    private final String cardAdjusted;

    public Hand(String cards, int bid, String cardAdjusted) {
        this.cards = cards;
        this.bid = bid;
        this.cardAdjusted = cardAdjusted;
        this.value = this.analyze();
    }

    public String analyze() {
        Map<Character, Integer> map = new HashMap<>();
        int jokerCount = 0;
        for (int i = 0; i < this.cardAdjusted.length(); i++) {
            char ch = this.cardAdjusted.charAt(i);
            if (ch == 'Z') {
                jokerCount++;
            } else {
                if (map.containsKey(ch)) {
                    int count = map.get(ch);
                    count++;
                    map.replace(ch, count);
                } else {
                    map.put(ch, 1);
                }
            }
        }
        Comparator<Integer> biggerFirst = ((p1, p2) -> p2 - p1);
        String prac;
        if (!map.isEmpty()) {
            prac = map.values().stream().sorted(biggerFirst).map(String::valueOf).collect(Collectors.joining());
        } else {
            prac = "0";
        }
        int i = jokerCount + Integer.parseInt(prac.substring(0, 1));
        return (i + prac.substring(1));
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand h) {
        int i = h.value.compareTo(this.value);
        if (i != 0) return i;
        return this.cardAdjusted.compareTo(h.cardAdjusted);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards='" + cards + '\'' +
                ", bid=" + bid +
                ", value='" + value + '\'' +
                ", cardAdjusted='" + cardAdjusted + '\'' +
                '}';
    }
}

package com.example.bottomnavigationproper;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Soundexer {

    public Soundexer(){

    }

    // find word in dictionary that is the closest match to word provided
    @RequiresApi(api = Build.VERSION_CODES.N)
    public HashMap<Integer, List<String>> getWordRatings(String testWord, List<String> dictionary) {
        // input word and wordlist
        // return hashmap of key=ratings and list of words matching that rating.

        String wordDex = encode(testWord);
        HashMap<Integer, List<String>> ratings = new HashMap<>();

        for(String dWord : dictionary) {
            String dWordDex = encode(dWord);
            int rating = difference(wordDex, dWordDex);
            if(ratings.containsKey(rating)) {
                List<String> words = ratings.get(rating);
                assert words != null;
                words.add(dWord);
                ratings.replace(rating, words);
            } else {
                List<String> words = new ArrayList<>();
                words.add(dWord);
                ratings.put(rating,words);
            }
        }
        return ratings;
    }

    public int difference(String word1, String word2) {
        // take twp encoded words and return a difference
        // compare each char in each word = when different reduce rating by 1
        // cap rating at zero if it falls below zero.
        char[] wordChars1 = word1.toCharArray();
        char[] wordChars2 = word2.toCharArray();
        int rating = 4;

        for(int i = 0; i<wordChars1.length && i<wordChars2.length;i++)
            if (wordChars1[i] != wordChars2[i]) rating--;

        return Math.max(rating, 0);
    }

    public String encode(String value) {
        String code = encodeString(value);
        code = removeDuplicates(code);
        code = fillOutToFour(code);
        return code;
    }

    public String fillOutToFour(String code) {

        switch(code.length()) {
            case 1:
                code += "000";
                break;
            case 2:
                code += "00";
                break;
            case 3:
                code += "0";
                break;
        }
        return code;
    }

    public String encodeString(String value) {
        value = value.toLowerCase();
        StringBuilder encoded = new StringBuilder();
        char[] chars = value.toCharArray();
        // get first char - change to uppercase, and make this first char in encoded string.
        encoded.append(Character.toUpperCase(chars[0]));


        for (int i = 1 ; i < chars.length;i++) {
            switch(chars[i]) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                case 'h':
                case 'w':
                case 'y':
                    break;
                case 'b':
                case 'f':
                case 'p':
                case 'v':
                    encoded.append("1");
                    break;
                case 'c':
                case 'g':
                case 'j':
                case 'k':
                case 'q':
                case 's':
                case 'x':
                case 'z':
                    encoded.append("2");
                    break;
                case 'd':
                case 't':
                    encoded.append("3");
                    break;
                case 'l':
                    encoded.append("4");
                    break;
                case 'm':
                case 'n':
                    encoded.append("5");
                    break;
                case 'r':
                    encoded.append("6");
                    break;


            }
        }
        return encoded.toString();
    }

    public String removeDuplicates(String value) {
        StringBuilder encoded = new StringBuilder();
        char[] chars = value.toCharArray();

        char prev = chars[0];
        encoded.append(prev);
        for(int i = 1 ; i<chars.length; i++) {
            if(prev != chars[i]) {
                prev = chars[i];
                encoded.append(chars[i]);
            }
        }
        return encoded.toString();
    }
}

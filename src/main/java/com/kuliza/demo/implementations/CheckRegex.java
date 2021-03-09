package com.kuliza.demo.implementations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckRegex {

    public int checkR(String words, Map<String, Integer> map) {
        int cnt = 0;
        for (String s : map.keySet()) {
            Pattern p = Pattern.compile(words);
            Matcher m = p.matcher(s);
            if (m.matches() == true) cnt += map.get(s);
        }
        return cnt;
    }


}

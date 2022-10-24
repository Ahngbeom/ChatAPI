package com.example.chatapi.DTO;

import java.util.*;

public class MBTICode {
    private static Map<String, String> CODES_MAP = new HashMap<String, String>();

    //    public static Set<String> get(String code) {
//        return Collections.singleton(CODES_MAP.get(code));
//    }
    public static Set<String> ALL = CODES_MAP.keySet();

    public static Map<String, String> getCodesMap() {
        return CODES_MAP;
    }

    public static Set<String> matchCode(String regExp) {
        Set<String> matchCodes = new HashSet<>();
        CODES_MAP.forEach((key, value) -> {
            if (key.matches(regExp))
                matchCodes.add(value);
        });
        return matchCodes;
    }
}

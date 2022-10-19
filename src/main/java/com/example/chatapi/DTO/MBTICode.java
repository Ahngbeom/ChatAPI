package com.example.chatapi.DTO;

import java.util.*;

public class MBTICode {
    public static final String ENFJ = "ENFJ";
    public static final String ENFP = "ENFP";
    public static final String ENTJ = "ENTJ";
    public static final String ENTP = "ENTP";
    public static final String ESFJ = "ESFJ";
    public static final String ESFP = "ESFP";
    public static final String ESTJ = "ESTJ";
    public static final String ESTP = "ESTP";
    public static final String INFJ = "INFJ";
    public static final String INFP = "INFP";
    public static final String INTJ = "INTJ";
    public static final String INTP = "INTP";
    public static final String ISFJ = "ISFJ";
    public static final String ISFP = "ISFP";
    public static final String ISTJ = "ISTJ";
    public static final String ISTP = "ISTP";
    private static final Map<String, String> CODES_MAP = new HashMap<String, String>() {{
        put("ENFJ", "ENFJ");
        put("ENFP", "ENFP");
        put("ENTJ", "ENTJ");
        put("ENTP", "ENTP");
        put("ESFJ", "ESFJ");
        put("ESFP", "ESFP");
        put("ESTJ", "ESTJ");
        put("ESTP", "ESTP");
        put("INFJ", "INFJ");
        put("INFP", "INFP");
        put("INTJ", "INTJ");
        put("INTP", "INTP");
        put("ISFJ", "ISFJ");
        put("ISFP", "ISFP");
        put("ISTJ", "ISTJ");
        put("ISTP", "ISTP");
    }};

    //    public static Set<String> get(String code) {
//        return Collections.singleton(CODES_MAP.get(code));
//    }
    public static final Set<String> ALL = CODES_MAP.keySet();

    public static Set<String> matchCode(String regExp) {
        Set<String> matchCodes = new HashSet<>();
        CODES_MAP.forEach((key, value) -> {
            if (key.matches(regExp))
                matchCodes.add(value);
        });
        return matchCodes;
    }
}

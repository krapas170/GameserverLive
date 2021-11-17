package com.minecraftstatus;

import java.util.HashMap;

public class Console {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String ORGANE = "\033[48:5:208m%s\033[m\n";

    private static String mark = "%";
    private static final HashMap<String, String> colors = new HashMap<>();

    static {
        colors.put("reset", RESET);
        colors.put("black", BLACK);
        colors.put("red", RED);
        colors.put("green", GREEN);
        colors.put("yellow", YELLOW);
        colors.put("blue", BLUE);
        colors.put("purple", PURPLE);
        colors.put("cyan", CYAN);
        colors.put("white", WHITE);
        colors.put("orange", ORGANE);
    }

    public static void test(String test) {
        println("%reset%[%green%TEST%reset%] " + test);
    } 

    public static void warn(String warn) {
        println("%reset%[%red%WARN%reset%] " + warn);
    }

    public static void info(String info) {
        println("%reset%[%blue%INFO%reset%] " + info);
    }

    public static void println(String text) {
        System.out.println(prep(text));
    }

    private static String prep(String text) {
        String[] colorKeys = colors.keySet().toArray(new String[0]);
        for (int i = 0; i < colorKeys.length; i++)
            text = text.replaceAll(mark + colorKeys[i] + mark, colors.get(colorKeys[i]));
        return text;
    }
}


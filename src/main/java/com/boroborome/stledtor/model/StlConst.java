package com.boroborome.stledtor.model;

import com.boroborome.stledtor.util.IndicatorIterator;

import java.util.Arrays;
import java.util.List;

public class StlConst {
    private static final List<Character> BlockEnd = Arrays.asList('[', ']', '{', '}', '\n');

    public static boolean isOperator(String param) {
        return param != null && param.length() == 1 && IndicatorIterator.isOperator(param.charAt(0));
    }

    public static boolean isBlockEnd(String param) {
        return param != null && param.length() == 1 && BlockEnd.contains(param.charAt(0));
    }
}

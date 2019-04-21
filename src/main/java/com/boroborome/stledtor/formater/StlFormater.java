package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.TabPrintStream;

import java.util.HashMap;
import java.util.Map;

public abstract class StlFormater<T extends StlExpression> {
    private static final Map<Class, StlFormater> formaterMap = initializeFormaterMap();

    protected abstract void doFormat(T expression, TabPrintStream output, Map<String, String> symbolMap);

    public static void format(StlExpression conditionExpression, TabPrintStream output, Map<String, String> symbolMap) {
        StlFormater formater = formaterMap.get(conditionExpression.getClass());
        formater.doFormat(conditionExpression, output, symbolMap);
    }

    private static Map<Class, StlFormater> initializeFormaterMap() {
        Map<Class, StlFormater> formaterMap = new HashMap<>();
        formaterMap.put(CommandExpression.class, new CommandExpressionFormater());
        formaterMap.put(ConditionExpression.class, new ConditionExpressionFormater());
        return formaterMap;
    }
}

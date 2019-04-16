package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public abstract class StlFormater<T extends StlExpression> {
    private static final Map<Class, StlFormater> formaterMap = initializeFormaterMap();

    protected abstract void doFormat(T expression, PrintStream output);

    public static void format(StlExpression conditionExpression, PrintStream output) {
        StlFormater formater = formaterMap.get(conditionExpression.getClass());
        formater.doFormat(conditionExpression, output);
    }

    private static Map<Class, StlFormater> initializeFormaterMap() {
        Map<Class, StlFormater> formaterMap = new HashMap<>();
        formaterMap.put(CommandExpression.class, new CommandExpressionFormater());
        formaterMap.put(ConditionExpression.class, new ConditionExpressionFormater());
        return formaterMap;
    }
}

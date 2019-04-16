package com.boroborome.stledtor.factory;

import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.IndicatorIterator;

import java.util.Arrays;
import java.util.List;

public abstract class StlExpressionFactory {
    private static final List<StlExpressionFactory> allFactorys = Arrays.asList(
            new CommandExpressionFactory(),
            new ConditionExpressionFactory()
    );
    public abstract StlExpression loadExpression(IndicatorIterator indicatorIterator);


    public static <T extends StlExpression> T parseExpression(IndicatorIterator indicatorIterator, Class<T> expressionType) {
        StlExpression expression = parseExpression(indicatorIterator);
        if (expression == null || !expression.getClass().equals(expressionType)) {
            throw new IllegalArgumentException("Can't read expect type:" + expressionType);
        }
        return (T) expression;
    }

    public static StlExpression parseExpression(IndicatorIterator indicatorIterator) {
        for (StlExpressionFactory factory : allFactorys) {
            StlExpression expression = factory.loadExpression(indicatorIterator);
            if (expression != null) {
                return expression;
            }
        }
        throw new IllegalArgumentException("Unsupported command:" + indicatorIterator.locateMessage());
    }
}

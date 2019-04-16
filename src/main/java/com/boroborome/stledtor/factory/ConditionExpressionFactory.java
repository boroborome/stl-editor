package com.boroborome.stledtor.factory;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.IndicatorIterator;

import java.util.List;
import java.util.Objects;

public class ConditionExpressionFactory extends StlExpressionFactory {
    @Override
    public StlExpression loadExpression(IndicatorIterator indicatorIterator) {
        if (!"[".equals(indicatorIterator.current())) {
            return null;
        }

        ConditionExpression conditionExpression = new ConditionExpression();
        indicatorIterator.ignore("[");
        conditionExpression.setCriteriaExpression(StlExpressionFactory.parseExpression(indicatorIterator, CommandExpression.class));
        indicatorIterator.ignore("]");
        indicatorIterator.ignore("{");
        loadExpressions(conditionExpression, indicatorIterator);
        indicatorIterator.ignore("}");
        return conditionExpression;
    }

    private void loadExpressions(ConditionExpression conditionExpression, IndicatorIterator indicatorIterator) {
        List<StlExpression> expressionList = conditionExpression.getExpressionList();
        while (indicatorIterator.hasNext() && !Objects.equals(indicatorIterator.current(), "}")) {
            expressionList.add(StlExpressionFactory.parseExpression(indicatorIterator));
        }
    }
}

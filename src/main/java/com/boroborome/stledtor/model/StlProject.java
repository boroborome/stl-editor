package com.boroborome.stledtor.model;

import com.boroborome.stledtor.factory.StlExpressionFactory;
import com.boroborome.stledtor.util.IndicatorIterator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StlProject {
    private List<ConditionExpression> conditionExpressionList = new ArrayList<>();

    public void initialize(IndicatorIterator indicatorIterator) {
        while (indicatorIterator.hasNext()) {
            conditionExpressionList.add(StlExpressionFactory.parseExpression(indicatorIterator, ConditionExpression.class));
        }
    }
}

package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.TabPrintStream;

public class ConditionExpressionFormater extends StlFormater<ConditionExpression> {
    @Override
    protected void doFormat(ConditionExpression expression, TabPrintStream output) {
        StlFormater.format(expression.getCriteriaExpression(), output);

        output.increaseTab();
        boolean first = true;
        for (StlExpression subExp : expression.getExpressionList()) {
            if (!first && output.getTabs() > 1) {
                output.println("AENO");
            }
            first = false;
            StlFormater.format(subExp, output);
        }
        output.descreaseTab();
    }
}

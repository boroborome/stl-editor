package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;

import java.io.PrintStream;

public class ConditionExpressionFormater extends StlFormater<ConditionExpression> {
    @Override
    protected void doFormat(ConditionExpression expression, PrintStream output) {
        StlFormater.format(expression.getCriteriaExpression(), output);
        output.println("LPS");

        boolean first = true;
        for (StlExpression subExp : expression.getExpressionList()) {
            if (!first) {
                output.println("AENO");
            }
            first = false;
            StlFormater.format(subExp, output);
        }
        output.println("LPP");
    }
}

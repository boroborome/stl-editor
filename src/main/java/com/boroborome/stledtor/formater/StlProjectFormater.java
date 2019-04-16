package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlProject;

import java.io.PrintStream;
import java.text.MessageFormat;

public class StlProjectFormater {
    public void format(StlProject project, PrintStream output) {
        int networkIndex = 1;
        for (ConditionExpression conditionExpression : project.getConditionExpressionList()) {
            output.println(MessageFormat.format("Network {0}", networkIndex++));
            StlFormater.format(conditionExpression, output);
        }
    }
}

package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.TabPrintStream;

import java.util.ArrayList;
import java.util.List;

public class ConditionExpressionFormater extends StlFormater<ConditionExpression> {
    @Override
    protected void doFormat(ConditionExpression expression, TabPrintStream output) {
        StlFormater.format(expression.getCriteriaExpression(), output);

        if (containsConditionExpression(expression.getExpressionList())) {
            formatConditionExpList(expression.getExpressionList(), output);
        } else {
            formatExpBlock(expression.getExpressionList(), output);
        }
    }

    private void formatConditionExpList(List<StlExpression> expressionList, TabPrintStream output) {
        List<List<StlExpression>> groupExpList = groupExpressions(expressionList);
        boolean useGroupCmd = groupExpList.size() > 1;

        if (useGroupCmd) {
            output.writeTabs().println("LPS");
        }
        boolean first = true;
        for (List<StlExpression> groupExp : groupExpList) {
            if (!first && useGroupCmd) {
                output.writeTabs().println("LRD");
            }
            first = false;

            if (useGroupCmd) {
                output.increaseTab();
            }
            formatExpBlock(groupExp, output);
            if (useGroupCmd) {
                output.descreaseTab();
            }
        }
        if (useGroupCmd) {
            output.writeTabs().println("LPP");
        }
    }

    private List<List<StlExpression>> groupExpressions(List<StlExpression> expressionList) {
        List<List<StlExpression>> groupExpList = new ArrayList<>();

        Class preType = null;
        List<StlExpression> preList = null;
        for (StlExpression expression : expressionList) {
            Class curType = expression.getClass();
            if (preType != curType || preType == ConditionExpression.class) {
                if (preList != null) {
                    groupExpList.add(preList);
                }
                preType = curType;
                preList = new ArrayList<>();
            }
            preList.add(expression);
        }

        if (preList != null) {
            groupExpList.add(preList);
        }
        return groupExpList;
    }

    private boolean containsConditionExpression(List<StlExpression> expressionList) {
        for (StlExpression subExp : expressionList) {
            if (subExp instanceof ConditionExpression) {
                return true;
            }
        }
        return false;
    }

    private void formatExpBlock(List<StlExpression> expressionList, TabPrintStream output) {
        boolean first = true;
        for (StlExpression subExp : expressionList) {
            if (!first && output.getTabs() > 0) {
                output.writeTabs().println("AENO");
            }
            first = false;
            StlFormater.format(subExp, output);
        }
    }
}

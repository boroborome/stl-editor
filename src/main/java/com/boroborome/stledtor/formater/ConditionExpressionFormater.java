package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.ConditionExpression;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.TabPrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConditionExpressionFormater extends StlFormater<ConditionExpression> {
    @Override
    protected void doFormat(ConditionExpression expression, TabPrintStream output, Map<String, String> symbolMap) {
        StlFormater.format(expression.getCriteriaExpression(), output, symbolMap);

        if (containsConditionExpression(expression.getExpressionList())) {
            formatConditionExpList(expression.getExpressionList(), output, symbolMap);
        } else {
            formatExpBlock(expression.getExpressionList(), output, symbolMap);
        }
    }

    private void formatConditionExpList(List<StlExpression> expressionList, TabPrintStream output, Map<String, String> symbolMap) {
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
            formatExpBlock(groupExp, output, symbolMap);
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

    private void formatExpBlock(List<StlExpression> expressionList, TabPrintStream output, Map<String, String> symbolMap) {
        boolean first = true;
        for (StlExpression subExp : expressionList) {
            if (!first && output.getTabs() > 0) {
                output.writeTabs().println("AENO");
            }
            first = false;
            StlFormater.format(subExp, output, symbolMap);
        }
    }
}

package com.boroborome.stledtor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConditionExpression extends StlExpression {
    private CommandExpression criteriaExpression = new CommandExpression();
    private List<StlExpression> expressionList = new ArrayList<>();
}

package com.boroborome.stledtor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StlConditionExpression {
    private CirteriaExpression cirteriaExpression;
    private List<StlExpression> expressionList = new ArrayList<>();
}

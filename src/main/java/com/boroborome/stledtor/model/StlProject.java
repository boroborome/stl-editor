package com.boroborome.stledtor.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StlProject {
    private List<StlConditionExpression> conditionExpressionList = new ArrayList<>();
}

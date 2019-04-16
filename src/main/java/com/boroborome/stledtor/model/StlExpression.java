package com.boroborome.stledtor.model;

import com.boroborome.stledtor.util.IndicatorIterator;

public abstract class StlExpression {
    abstract boolean isStartIndicator(String indicator);
    abstract StlExpression parse(IndicatorIterator indicatorIterator);
}

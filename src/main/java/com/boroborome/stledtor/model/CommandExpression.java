package com.boroborome.stledtor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandExpression extends StlExpression {
    private String command;
    private List<String> params = new ArrayList<>();
}

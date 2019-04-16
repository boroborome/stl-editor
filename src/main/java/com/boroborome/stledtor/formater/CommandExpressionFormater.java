package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.CommandExpression;

import java.io.PrintStream;

public class CommandExpressionFormater extends StlFormater<CommandExpression> {
    @Override
    protected void doFormat(CommandExpression expression, PrintStream output) {
        output.print(expression.getCommand());
        output.print("\t");

        boolean firstParam = true;
        for (String param : expression.getParams()) {
            if (!firstParam) {
                output.print(",");
            }
            output.print(param);
            firstParam = false;
        }
        output.println();
    }
}

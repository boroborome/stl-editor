package com.boroborome.stledtor.formater;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.util.TabPrintStream;

import java.util.Map;

public class CommandExpressionFormater extends StlFormater<CommandExpression> {
    @Override
    protected void doFormat(CommandExpression expression, TabPrintStream output, Map<String, String> symbolMap) {
        output.writeTabs().print(expression.getCommand());
        output.print("\t");

        boolean firstParam = true;
        for (String param : expression.getParams()) {
            if (!firstParam) {
                output.print(",");
            }

            String realParam = symbolMap.getOrDefault(param, param);
            output.print(realParam);
            firstParam = false;
        }
        output.println();
    }
}

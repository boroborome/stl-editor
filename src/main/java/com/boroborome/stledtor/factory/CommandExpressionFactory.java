package com.boroborome.stledtor.factory;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.model.StlConst;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.IndicatorIterator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandExpressionFactory extends StlExpressionFactory {
    private static final Set<String> COMMAND_LIST = allCommand();

    @Override
    public StlExpression loadExpression(IndicatorIterator indicatorIterator) {
        if (!COMMAND_LIST.contains(indicatorIterator.current())) {
            return null;
        }
        CommandExpression commandExpression = new CommandExpression();
        commandExpression.setCommand(indicatorIterator.current());
        List<String> params = commandExpression.getParams();
        for (String param = indicatorIterator.next();
             param != null && !StlConst.CommandFinish.equals(param);
             param = indicatorIterator.next()) {
            params.add(param);
        }
        return commandExpression;
    }

    private static Set<String> allCommand() {
        Set<String> commandList = new HashSet<>();
        commandList.add("LD");
        commandList.add("A");
        commandList.add("-D");
        commandList.add("+D");
        commandList.add("*D");
        commandList.add("/D");
        commandList.add("S");
        return commandList;
    }
}

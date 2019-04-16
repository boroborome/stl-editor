package com.boroborome.stledtor.factory;

import com.boroborome.stledtor.model.CommandExpression;
import com.boroborome.stledtor.model.StlConst;
import com.boroborome.stledtor.model.StlExpression;
import com.boroborome.stledtor.util.IndicatorIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandExpressionFactory extends StlExpressionFactory {
    private static final Set<String> COMMAND_LIST = allCommand();

    @Override
    public StlExpression loadExpression(IndicatorIterator indicatorIterator) {
        if (!COMMAND_LIST.contains(indicatorIterator.current())) {
            return null;
        }
        CommandExpression commandExpression = new CommandExpression();
        commandExpression.setCommand(indicatorIterator.next());
        List<String> params = commandExpression.getParams();
        for (String param = indicatorIterator.current();
             param != null && !StlConst.isBlockEnd(param); ) {
            params.add(param);
            indicatorIterator.next();
            param = indicatorIterator.current();
            if (Objects.equals(param, ",")) {
                indicatorIterator.ignore(",");
                param = indicatorIterator.current();
            } else {
                break;
            }
        }
        return commandExpression;
    }

    private static Set<String> allCommand() {
        Set<String> commandList = new HashSet<>();
        BufferedReader configReader = new BufferedReader(
                new InputStreamReader(
                CommandExpressionFactory.class.getResourceAsStream("/commands.txt")));
        try {
            for (String command = configReader.readLine();
                 command != null;
                 command = configReader.readLine()) {
                commandList.add(command);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandList;
    }
}

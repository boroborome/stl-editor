package com.boroborome.stledtor;

import com.boroborome.stledtor.formater.StlProjectFormater;
import com.boroborome.stledtor.model.StlProject;
import com.boroborome.stledtor.util.IndicatorIterator;
import com.boroborome.stledtor.util.LocatedStream;
import com.boroborome.stledtor.util.TabPrintStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//@SpringBootApplication
public class StlEdtorApplication {

    public static void main(String[] args) throws IOException {
//		SpringApplication.run(StlEdtorApplication.class, args);
        if (args.length < 2) {
            System.out.println("stl-editor input.stl output.plc vars.txt");
        }

        IndicatorIterator indicatorIterator = loadIndicatorIterator(args[0]);
        TabPrintStream output = creatOutput(args[1]);
        Map<String, String> symbolMap = new HashMap<>();
        if (args.length > 2) {
            symbolMap = loadSymbolMap(args[2]);
        }
        StlProject project = loadProject(indicatorIterator);
//		System.out.println(JSON.toJSONString(project));
        new StlProjectFormater().format(project, output, symbolMap);
    }

    private static Map<String, String> loadSymbolMap(String symbolFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(symbolFile)));
        Map<String, String> symbolMap = new HashMap<>();

        for (String line = reader.readLine();
             line != null;
             line = reader.readLine()) {
            String[] items = line.split("\t");
            if (items.length > 1) {
                symbolMap.put(items[0], items[1]);
            }
        }
        return symbolMap;
    }

    private static StlProject loadProject(IndicatorIterator indicatorIterator) {
        StlProject project = new StlProject();
        project.initialize(indicatorIterator);
        return project;
    }

    private static TabPrintStream creatOutput(String outputFile) throws FileNotFoundException {
        return new TabPrintStream(new FileOutputStream(new File(outputFile)), true);
    }

    private static IndicatorIterator loadIndicatorIterator(String inputFile) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(inputFile));
        return new IndicatorIterator(new LocatedStream(fileInputStream));
    }

}

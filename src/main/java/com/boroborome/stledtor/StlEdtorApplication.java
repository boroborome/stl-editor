package com.boroborome.stledtor;

import com.alibaba.fastjson.JSON;
import com.boroborome.stledtor.formater.StlProjectFormater;
import com.boroborome.stledtor.model.StlProject;
import com.boroborome.stledtor.util.IndicatorIterator;
import com.boroborome.stledtor.util.LocatedStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

//@SpringBootApplication
public class StlEdtorApplication {

	public static void main(String[] args) throws FileNotFoundException {
//		SpringApplication.run(StlEdtorApplication.class, args);
		if (args.length != 3) {
			System.out.println("stl-editor input vars output");
		}

		IndicatorIterator indicatorIterator = loadIndicatorIterator(args[0]);
		PrintStream output = creatOutput(args[2]);

		StlProject project = loadProject(indicatorIterator);
		System.out.println(JSON.toJSONString(project));
		new StlProjectFormater().format(project, output);
	}

	private static StlProject loadProject(IndicatorIterator indicatorIterator) {
		StlProject project = new StlProject();
		project.initialize(indicatorIterator);
		return project;
	}

	private static PrintStream creatOutput(String outputFile) throws FileNotFoundException {
		return new PrintStream(new FileOutputStream(new File(outputFile)), true);
	}

	private static IndicatorIterator loadIndicatorIterator(String inputFile) throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(new File(inputFile));
		return new IndicatorIterator(new LocatedStream(fileInputStream));
	}

}

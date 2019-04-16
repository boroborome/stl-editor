package com.boroborome.stledtor;

import com.boroborome.stledtor.util.IndicatorIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;

//@RunWith(SpringRunner.class)
public class StlEdtorApplicationTests {

	@Test
	public void iterator_should_return_all_indicator() {
		IndicatorIterator indicatorIterator = new IndicatorIterator(StlEdtorApplicationTests.class.getResourceAsStream("/normal-project.txt"));
		while (indicatorIterator.hasNext()) {
			System.out.println(indicatorIterator.next());
		}
	}

}

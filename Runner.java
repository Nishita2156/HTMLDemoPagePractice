package com.demo.bdd;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

public class Runner {
	@CucumberOptions(
			  features = { "src/main/java/com/demo/features"},
			  glue = {"com.demo.bdd"},
			  plugin = {"pretty", "html:target/cucumber-reports.html"},
			  monochrome = true
			)
			public class TestNGRunner extends AbstractTestNGCucumberTests {
			}


}

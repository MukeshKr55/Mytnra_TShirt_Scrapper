package com.myntra.scraper.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.myntra.scraper.steps",
        plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"}
)
public class TestRunner {
}

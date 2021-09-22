package com.cmcp.test.runCukes.frontEnd;



import com.cmcp.test.framework.tags.FrontEnd;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "@FrontEndTask_TC01_Test",
        stepNotifications = true,
        plugin = {"pretty",
                "json:target/cucumber-report/FrontEndTask_TC01_Test.json"},
        features = {"src/test/resources/features/FrontEndTasks.feature"},
        glue = {"com.cmcp.test"}
)
@Category({FrontEnd.class})
public class FrontEndTask_TC01_Test {
}

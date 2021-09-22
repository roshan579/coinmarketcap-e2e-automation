package com.cmcp.test.runCukes.backEnd;



import com.cmcp.test.framework.tags.BackEnd;
import com.cmcp.test.framework.tags.FrontEnd;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "@BackEndTask_TC01_Test",
        stepNotifications = true,
        plugin = {"pretty",
                "json:target/cucumber-report/BackEndTask_TC01_Test.json"},
        features = {"src/test/resources/features/BackEndTasks.feature"},
        glue = {"com.cmcp.test"}
)
@Category({BackEnd.class})
public class BackEndTask_TC01_Test {
}

package com.cmcp.test.framework.hooks;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Created by Roshan
 */
public class ScenarioHook {

    public static Scenario scenario;

    public static Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Before(order = 1)
    public void KeepSceario(Scenario scenario) {
        this.scenario = scenario;
        this.setScenario(scenario);
    }
}

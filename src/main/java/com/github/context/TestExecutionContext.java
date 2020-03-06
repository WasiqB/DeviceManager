package com.github.context;

import java.util.HashMap;
import java.util.logging.Logger;

import com.github.manager.AppiumDeviceManager;
import com.github.manager.AppiumDriverManager;
import io.appium.java_client.AppiumDriver;

public class TestExecutionContext {
    private final        String                  testName;
    private final        AppiumDriver            driver;
    private final        HashMap<String, Object> testExecutionState;
    private              String                  deviceId = "NOT-YET-SET";
    private static final Logger                  LOGGER   = Logger.getLogger(
        Class.class.getSimpleName());

    public TestExecutionContext(String testName) {
        SessionContext.addContext(Thread.currentThread()
            .getId(), this);
        this.testName = testName;
        this.testExecutionState = new HashMap<String, Object>();
        if (testName.equalsIgnoreCase(SessionContext.TEST_RUNNER)) {
            this.deviceId = "ATD-RUNNER";
            this.driver = null;
        } else {
            this.driver = AppiumDriverManager.getDriver();
            this.deviceId = AppiumDeviceManager.getAppiumDevice()
                .getDevice()
                .getUdid();
        }
        LOGGER.info(String.format("%s - TestExecution context created", testName));
    }

    public String getTestName() {
        return testName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void addTestState(String key, Object details) {
        testExecutionState.put(key, details);
    }

    public Object getTestState(String key) {
        return testExecutionState.get(key);
    }

    public String getTestStateAsString(String key) {
        return (String) testExecutionState.get(key);
    }
}

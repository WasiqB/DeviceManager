package com.github.utils;

import com.github.manager.AppiumManagerFactory;
import com.github.manager.IAppiumManager;

public class AvailablePorts {
    public int getAvailablePort(String hostMachine) throws Exception {
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostMachine);
        return appiumManager.getAvailablePort(hostMachine);
    }
}
